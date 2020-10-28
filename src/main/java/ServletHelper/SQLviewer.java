package ServletHelper;

import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

public class SQLviewer {
    private Connection con;
    private Role userRole = Role.NULL;
    HashMap<Role, String> RoleAttachments;

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public SQLviewer(Connection con) {
        setCon(con);

        RoleAttachments = new HashMap<>();
        RoleAttachments.put(Role.DB_DOCTOR, "Доктор");
        RoleAttachments.put(Role.DB_PATIENT, "Пациент");
        RoleAttachments.put(Role.DB_ADMIN, "Администратор");

    }

    public static void createQuery(HashMap<String, String> ask) {
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("user", "guest");
        connectionProperties.setProperty("password", "");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String connectionUrl = "jdbc:sqlserver://127.0.0.1;database=Hospital;";
        try {
            Connection con = DriverManager.getConnection(connectionUrl, connectionProperties);
            Statement ps = con.createStatement();
            int demanded_role;
            String patronymicStr = "";
            String patronymicInsert = "";
            if(!ask.get("patron").isEmpty()) {
                patronymicInsert = "patronymic,";
                patronymicStr = "','" + ask.get("patron");
            }
            if(ask.get("role").equals("radio-staff")) {
                demanded_role = 0;
                ps.execute("INSERT INTO dbo.[Hospital.AccountQuery] (login, password, demanded_role, surname, name," +
                        patronymicInsert + "post)" +
                        "VALUES ('" + ask.get("nameNew") + "','" + ask.get("passNew") + "'," +
                        String.valueOf(demanded_role) + ",'" + ask.get("surname") + "','" +
                        ask.get("name") + patronymicStr + "','" + ask.get("post") + "')");
            }
            else {
                demanded_role = 1;
                ps.execute("INSERT INTO dbo.[Hospital.AccountQuery] (login, password, demanded_role, surname, name," +
                        patronymicInsert + "email, address, reason, [card ID])" +
                        "VALUES ('" + ask.get("nameNew") + "','" + ask.get("passNew") + "'," +
                        String.valueOf(demanded_role) + ",'" + ask.get("surname") + "','" +
                        ask.get("name") + patronymicStr + "','" + ask.get("email") + "','" +
                        ask.get("address") + "','" + ask.get("problem") + "','" + ask.get("data") + "')");
            }

            ps.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void discardQuery() {
        if(con == null || userRole != Role.DB_ADMIN)
            return;
    }

    public void aproveQuery() {
        if(con == null || userRole != Role.DB_ADMIN)
            return;
        Statement statement;
        ResultSet resSet;
        try {
            statement = con.createStatement();
            String username = "";
            String password = "ytwog123";
            username = "ytwog";
            String database = "Hospital";
            resSet = statement.executeQuery("SELECT email FROM [Hospital.AccountQuery]");
            if(!resSet.next()) {
                statement.close();
                return; // Если список запросов пуст, то завершаем изменение
            }
            username = resSet.getString("email");
            statement.executeUpdate("CREATE LOGIN "+username+" WITH PASSWORD = '"+password+"'");
            statement.executeUpdate("USE HOSPITAL CREATE USER "+username+" FOR LOGIN "+username+"");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public enum Role {
        DB_DOCTOR,
        DB_PATIENT,
        DB_ADMIN,
        NULL
    };


    public Role setupUser() {
        String res = "";
        if (con == null) {
            return Role.NULL;
        }
        Statement statement = null;
        try {
            statement = con.createStatement();
            String []sqlRolenames = {"SELECT IS_ROLEMEMBER ('db_doctor') AS ROLE",
                    "SELECT IS_ROLEMEMBER ('db_patient') AS ROLE",
                    "SELECT IS_ROLEMEMBER ('db_accessadmin') AS ROLE"};
            for(int i = 0; i < 3; i++) {
                ResultSet resSet = statement.executeQuery(sqlRolenames[i]);
                if(resSet.next() && (resSet.getBoolean("ROLE"))) {
                      setUserRole(Role.values()[i]);
                      statement.close();
                      return getUserRole();
                }
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getUserRole();
    }

    private String filterNulls(String ask) {
        if(ask == null) {
            return "Не указано";
        } else return ask;
    }

    public String generateResponse(blockGenerator responseDealer, String datatype) {
        if (con == null) return "";
        try {
            Statement statement;
            ResultSet resSet;
            if(datatype.equals("My Profile")) { // Если выводится профиль пользователя
                if(userRole == Role.DB_DOCTOR) { // Доктор
                    statement = con.createStatement();
                    resSet = statement.executeQuery("SELECT * FROM View_Staff\n");
                }
                else if(userRole == Role.DB_PATIENT) { // Пациент
                    statement = con.createStatement();
                    resSet = statement.executeQuery("SELECT * FROM View_Patient\n");
                }
                else return "";
                String returnString = "";
                if (resSet.next()) {
                    returnString = responseDealer.generateBlock(0, "Мой профиль",
                            resSet.getString("Surname"),
                            resSet.getString("Name") + resSet.getString("Patronymic"));
                }
                statement.close();
                return returnString;
            }
            else if(datatype.equals("My Post")) { // Если выводится должность и роль пользователя
                String [] valueArr = {};
                if(userRole == Role.DB_DOCTOR) { // Доктор
                    statement = con.createStatement();
                    resSet = statement.executeQuery("SELECT * FROM View_Staff\n");
                    if (resSet.next()) { // Если есть элементы таблицы, которые можно считать
                        valueArr = new String[]{"Общие данные", RoleAttachments.get(userRole),
                                resSet.getString("Post"),
                                resSet.getString("login")
                        };
                    }
                    statement.close();
                } else if(userRole == Role.DB_PATIENT) { // Пациент
                    statement = con.createStatement();
                    resSet = statement.executeQuery("SELECT * FROM View_Patient\n");
                    if (resSet.next()) { // Если есть элементы таблицы, которые можно считать
                        valueArr = new String[]{"Общие данные", RoleAttachments.get(userRole),
                                "Должность: " + resSet.getString("Post"),
                                "Логин:     " + resSet.getString("Login"),
                                "Адрес:     " + resSet.getString("Address"),
                                "email:     " + filterNulls(resSet.getString("email"))};
                    }
                    statement.close();
                } else if(userRole == Role.DB_ADMIN) {
                    valueArr = new String[]{RoleAttachments.get(userRole)};
                } else return "";

                return (responseDealer.generateBlock(1, valueArr));
            } if(datatype.equals("Action Form")) { // Если выводится должность и роль пользователя
                String [] valueArr = {};
                if(userRole == Role.DB_ADMIN) {
                    statement = con.createStatement();
                    resSet = statement.executeQuery("SELECT * FROM [Hospital.AccountQuery]\n");
                    if (resSet.next()) {
                        valueArr = new String[]{"Запросы", resSet.getString("email")};
                    } else {
                        valueArr = new String[]{"Запросы", "Пусто"};
                    }
                    statement.close();
                } else return " ";

                return (responseDealer.generateBlock(2, valueArr));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return " ";
    }
}
