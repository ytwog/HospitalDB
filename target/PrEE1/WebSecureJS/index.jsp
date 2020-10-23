<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">WebSecurityLP</a>
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Overview</button>
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Theory</button>
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Courses</button>
    </nav>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>WS Laboratory Practicum</title>
    <link rel="stylesheet" href="bootstrap-4.5.0-dist/css/bootstrap.min.css">
    <link href="style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="p-4 soft-fluid">
    <div class="row shadow">

        <!-- Buying menu -->
        <div class="col-8 ">
            <div class="row justify-content-center">
                <div class="using-scfont col-6 text-white items-category bg-secondary border border-dark rounded" id="menu-buy-units">Practicum table</div>
                <div class="using-scfont col-6 text-white items-category bg-secondary border border-dark rounded" id="menu-buy-buildings">Optional</div>
            </div>
            <div class="row justify-content-center">
                <!--
                 <div class="col-4 text-light bg-dark m-1 col-md-2">
                     <img src="images/zDrone.png" alt="Zerg Drone" class="item-boxover">
                     <div>Drone</div>
                 </div>
                 <div class="col-4 text-light bg-dark m-1 col-md-2">
                     <div>Zergling</div>
                 </div> -->
            </div>
        </div>
        <!-- Buying menu -->
        <div class="col-4">
            <div class="container-fluid bg-info">Task order:</div>
            <div id="task-space"></div>
        </div>

    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="bootstrap-4.5.0-dist/js/bootstrap.min.js"></script>
<script src="script.js"></script>
</body>
</html>

