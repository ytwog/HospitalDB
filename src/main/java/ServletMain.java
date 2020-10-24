import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

public class ServletMain extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession sess = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.println("<html>");
        pw.println(response.getContentType());
        pw.println("<h1>");
        pw.println("Loading...!");
        pw.println("</h1>");
        pw.println("</html>");
        String userName = request.getParameter("name");
        String passWord = request.getParameter("pass");
        if(userName == null || userName.equals("")) {
            response.addHeader("additionalLoginInfo", "no data");
            forwardRestart(request, response);
            return;
        }
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("user", userName);
        connectionProperties.setProperty("password", passWord);
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String connectionUrl = "jdbc:sqlserver://127.0.0.1;database=Hospital;";
        Connection con = null;
        try {
            con = DriverManager.getConnection(connectionUrl, connectionProperties);


            sess.setAttribute("Connection", con);
            response.sendRedirect("/client");
            //reqDispatcher.forward(request, response);
        } catch (SQLException throwables) {
            response.addHeader("additionalLoginInfo", "wrong cred");
            forwardRestart(request, response);
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.addHeader("additionalLoginInfo", "no data");
        forwardRestart(request, response);

    }

    void forwardRestart(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
        try {
            RequestDispatcher reqDispatcher = request.getRequestDispatcher("/InputLab/index.jsp");
            reqDispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
