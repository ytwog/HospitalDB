<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.sql.*" %>
<%@ page import="ServletHelper.HtmlBlockGEnerator" %>
<!DOCTYPE html>
<html>
<head>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">HospitalLab</a>
    </nav>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>HospitalLab</title>
    <link rel="stylesheet" href="resource/bootstrap-4.5.0-dist/css/bootstrap.min.css">
    <link href="resource/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="p-4 soft-fluid">
    <div class="row shadow">
        <!-- Buying menu -->
        <div class="col-8 ">
            <div class="row justify-content-center">
                <div class="col-6 text-white items-category bg-secondary border border-dark rounded" id="menu-buy-units">
                    Staff data
                    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
                    <%
                        HttpSession sess = request.getSession();
                        Connection conCon = (Connection) sess.getAttribute("Connection");

                        try {
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        String connectionUrl = "jdbc:sqlserver://127.0.0.1;database=Hospital;";
                        try {
                            Statement statement = conCon.createStatement();

                            ResultSet resSet = statement.executeQuery("SELECT * FROM View_Staff\n");
                            PrintWriter pWriter = response.getWriter();

                            if(resSet.next()) // Если есть элементы таблицы, которые можно считать
                            out.println(HtmlBlockGEnerator.generateBlock("My profile",
                                    resSet.getString("Surname") + resSet.getString("Name") + resSet.getString("Patronymic"),
                                    resSet.getString("Post")));

                            while(resSet.next()) {
                                out.println(resSet.getString("Post"));
                                out.println(resSet.getString("Advance"));
                                out.println(resSet.getString("Wages"));
                            }

                            statement.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    %>
                </div>
                <div class="col-6 text-white items-category bg-secondary border border-dark rounded" id="menu-buy-buildings">Result
                    <%

                    %>
                </div>
            </div>
            <div class="row justify-content-center">
            </div>
        </div>

    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
</body>
</html>

