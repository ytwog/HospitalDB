package ServletHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLviewer {
    private Connection con;
    private Role userRole = Role.NULL;

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
    }

    public enum Role {
        DB_DOCTOR,
        DB_PATIENT,
        DB_ADMIN,
        NULL
    };

    public Role setupUser() {
        String res = "";
        if (con == null) return Role.NULL;
        Statement statement = null;
        try {
            statement = con.createStatement();
            String []sqlRolenames = {"SELECT IS_ROLEMEMBER ('db_datareader') AS 'ROLE'",
                    "SELECT IS_ROLEMEMBER ('db_patient') AS 'ROLE'",
                    "SELECT IS_ROLEMEMBER ('db_admin') AS 'ROLE'"};
            for(int i = 0; i < 3; i++) {
                ResultSet resSet = statement.executeQuery(sqlRolenames[i]);
                if(resSet.getString("ROLE").equals("1")) {
                    setUserRole(Role.values()[i]);
                    return getUserRole();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userRole;
    }

    public String generateResponse(blockGenerator responseDealer, String datatype) {
        if (con == null) return "";
        try {
            Statement statement = con.createStatement();
            ResultSet resSet = statement.executeQuery("SELECT * FROM View_Staff\n");
            if (resSet.next()) // Если есть элементы таблицы, которые можно считать
                return (responseDealer.generateBlock("Мой профиль",
                        resSet.getString("Surname") + resSet.getString("Name") + resSet.getString("Patronymic"),
                        resSet.getString("Post")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
}
