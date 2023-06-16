<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%
    List<Boat> boats = (List<Boat>) request.getAttribute("boats");
    String error = (String) request.getAttribute("error");
%>

<html lang="en">

<head>
    <!-- Required meta tags-->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="au theme template">
    <meta name="author" content="Hau Nguyen">
    <meta name="keywords" content="au theme template">

    <!-- Title Page-->
    <title> HARBOR </title>

    <!-- Fontfaces CSS-->
    <link href="asset/css/font-face.css" rel="stylesheet" media="all">
    <link href="asset/vendor/font-awesome-5/css/fontawesome-all.min.css" rel="stylesheet" media="all">
    <link href="asset/vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">
    <link href="asset/vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">

    <!-- Bootstrap CSS-->
    <link href="asset/vendor/bootstrap-4.1/bootstrap.min.css" rel="stylesheet" media="all">

    <!-- asset/Vendor CSS-->
    <link href="asset/vendor/animsition/animsition.min.css" rel="stylesheet" media="all">
    <link href="asset/vendor/bootstrap-progressbar/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet" media="all">
    <link href="asset/vendor/wow/animate.css" rel="stylesheet" media="all">
    <link href="asset/vendor/css-hamburgers/hamburgers.min.css" rel="stylesheet" media="all">
    <link href="asset/vendor/slick/slick.css" rel="stylesheet" media="all">
    <link href="asset/vendor/select2/select2.min.css" rel="stylesheet" media="all">
    <link href="asset/vendor/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet" media="all">

    <!-- Main CSS-->
    <link href="asset/css/theme.css" rel="stylesheet" media="all">

</head>

<body class="animsition">
    <div class="page-wrapper">
        <!-- HEADER MOBILE-->
        <!-- MENU SIDEBAR-->
        <aside class="menu-sidebar d-none d-lg-block">
            <div class="logo">
                <a href="#">
                    <h2> PEARL HARBOR </h2>
                </a>
            </div>
            <div class="menu-sidebar__content js-scrollbar1">
                <nav class="navbar-sidebar">
                    <ul class="list-unstyled navbar__list"> 
                        <li>
                            <a href="#">
                                <i class="far fa-check-square"></i> Nouvelle prevision 
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i class="far fa-check-square"></i> Les propositions
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </aside>
        <!-- END MENU SIDEBAR-->

        <!-- PAGE CONTAINER-->
        <div class="page-container">
            <!-- HEADER DESKTOP-->
            <header class="header-desktop">
                <div class="section__content section__content--p30">
                    <div class="container-fluid">
                        <div class="header-wrap">
                           <h5> Agence de port </h5>
                        </div>
                    </div>
                </div>
            </header>
            <!-- HEADER DESKTOP-->

            <!-- MAIN CONTENT-->
            <div class="main-content">
                <div class="section__content section__content--p30">
                    <div class="container-fluid">
                        <div class="row justify-content-center">
                            <div class="col-lg-6">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="card-title" style="margin-bottom: 50px;">
                                            <h3 class="text-center title-2"> Nouvelle prevision </h3>
                                        </div>
                                        <form action="New_prevision_ctrl" method="get" novalidate="novalidate">
                                            <div class="row form-group">
                                                <div class="col col-md-3">
                                                    <label for="selectSm" class=" form-control-label">Chosir bateau</label>
                                                </div>
                                                <div class="col-12 col-md-9">
                                                    <select name="boat_id" id="SelectLm" class="form-control-sm form-control">
                                                        <% for(Boat boat : boats){ %>
                                                            <option value="<%= boat.getId_boat() %>">
                                                                <%= boat.getBoat_name() %>
                                                            </option>
                                                        <% } %>
                                                    </select>
                                                </div>
                                            </div>


                                            <div class="form-group">
                                                <label for="cc-payment" class="control-label mb-1">Prevision d'arrive</label>
                                                <input id="cc-pament" name="arrive" type="datetime-local" class="form-control" aria-required="true" aria-invalid="false">
                                            </div>
                                            <div class="form-group">
                                                <label for="cc-payment" class="control-label mb-1">Previson de depart</label>
                                                <input id="cc-pament" name="depart" type="datetime-local" class="form-control" aria-required="true" aria-invalid="false">
                                            </div>

                                            <div>
                                                <button id="payment-button" type="submit" class="btn  btn-info">
                                                    Enregistrer
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="copyright">
                                    <p>Copyright Â© 2018 Colorlib. All rights reserved. Template by <a href="https://colorlib.com">Colorlib</a>.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <!-- Jquery JS-->
    <script src="asset/vendor/jquery-3.2.1.min.js"></script>
    <!-- Bootstrap JS-->
    <script src="asset/vendor/bootstrap-4.1/popper.min.js"></script>
    <script src="asset/vendor/bootstrap-4.1/bootstrap.min.js"></script>
    <!-- asset/Vendor JS       -->
    <script src="asset/vendor/slick/slick.min.js">
    </script>
    <script src="asset/vendor/wow/wow.min.js"></script>
    <script src="asset/vendor/animsition/animsition.min.js"></script>
    <script src="asset/vendor/bootstrap-progressbar/bootstrap-progressbar.min.js">
    </script>
    <script src="asset/vendor/counter-up/jquery.waypoints.min.js"></script>
    <script src="asset/vendor/counter-up/jquery.counterup.min.js">
    </script>
    <script src="asset/vendor/circle-progress/circle-progress.min.js"></script>
    <script src="asset/vendor/perfect-scrollbar/perfect-scrollbar.js"></script>
    <script src="asset/vendor/chartjs/Chart.bundle.min.js"></script>
    <script src="asset/vendor/select2/select2.min.js">
    </script>

    <!-- Main JS-->
    <script src="asset/js/main.js"></script>

</body>

</html>
<!-- end document-->
