package ServletHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

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
                      return getUserRole();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userRole;
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

                if (resSet != null && resSet.next()) // Если есть элементы таблицы, которые можно считать
                    return (responseDealer.generateBlock(0, "Мой профиль",
                            resSet.getString("Surname"),
                            resSet.getString("Name") + resSet.getString("Patronymic")));
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
                } else if(userRole == Role.DB_ADMIN) {
                    valueArr = new String[]{RoleAttachments.get(userRole)};
                } else return "";

                return (responseDealer.generateBlock(1, valueArr));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
}
