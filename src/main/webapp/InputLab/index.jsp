<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Properties" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="/feed">HospitalLab</a>
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
                    <%
                        out.println("<h1>Welcome at our Hospital:</h1>");
                        String loginReason = response.getHeader("additionalLoginInfo");
                        if(loginReason != null && loginReason.equals("wrong cred")) { // Если некорректные логин/пароль
                            out.println("Логин или пароль указаны неверно!");
                        }
                        out.println();
                        out.println("<br>");
                        out.println("<form action=\"feed\" method=\"POST\">");
                        out.println("<p>Name</p>");
                        out.println("<input type=\"text\" name=\"name\">");
                        out.println("<p>Password</p>");
                        out.println("<input type=\"password\" name=\"pass\">");

                        out.println("<input type=\"submit\" name=\"login\" value=\"Login\"><br>");
                        out.println("<a class=\"text-white\" href=\"/singup\">");
                        out.println("Регистрация");
                        out.println("</a>");
                        out.println("</form>");
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

