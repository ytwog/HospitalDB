<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.sql.*" %>
<%@ page import="ServletHelper.HtmlBlockGEnerator" %>
<%@ page import="ServletHelper.SQLviewer" %>
<%@ page import="ServletHelper.blockGenerator" %>
<!DOCTYPE html>
<html>
<head>
    <%
        // Базовая инициализация
        HttpSession sess = request.getSession();

        String optionLogout = request.getParameter("logout");
        String optionQuery = request.getParameter("createQuery");
        if (optionLogout != null) {
            sess.invalidate();
            sess = request.getSession(false);
            response.sendRedirect("/feed");
        }
        SQLviewer viewer = null;
        blockGenerator generator = new HtmlBlockGEnerator();
        SQLviewer.Role userRole = SQLviewer.Role.NULL;
        Connection conCon = null;

        if(sess != null) { // Если сессия клиента актуальна

            conCon = (Connection) sess.getAttribute("Connection");

            if (conCon == null) {
                response.sendRedirect("/feed");
            } else {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                String connectionUrl = "jdbc:sqlserver://127.0.0.1;database=Hospital;";

                viewer = new SQLviewer(conCon);
                userRole = viewer.setupUser();
            }
        }
    %>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="/feed">HospitalLab</a>
        <%
            // Установка специфичных для каждой роли кнопок для navbar
            if(userRole != SQLviewer.Role.NULL) {
                out.println("<form action=\"client\" method=\"GET\">");
                out.println("<input type=\"submit\" name=\"createQuery\" value=\"Создать обращение\">");
                out.println("<input type=\"submit\" name=\"logout\" value=\"Выйти\">");
                out.println("</form>");
            }
        %>
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
                    <div class="row">
                        <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
                        <%
                            if(conCon != null) {
                                try {
                                    Statement statement = conCon.createStatement();

                                    if (userRole.equals(SQLviewer.Role.DB_ADMIN)) {
                                        String optionDiscard = request.getParameter("discard");
                                        String optionAprove = request.getParameter("aprove");
                                        if (optionDiscard != null) {
                                            viewer.discardQuery();
                                        }
                                        if (optionAprove != null) {
                                            viewer.aproveQuery();
                                        }
                                    }

                                    out.println(viewer.generateResponse(generator, "My Profile"));
                                    out.println(viewer.generateResponse(generator, "My Post"));
                                    out.println(viewer.generateResponse(generator, "Action Form"));
                                    statement.close();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                        %>
                    </div>
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

