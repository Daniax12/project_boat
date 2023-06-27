

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
   // HttpSession session = request.getSession();
    String getPath = (String) request.getParameter("page");
    if(getPath == null) getPath = "infoGeneral";
    String theFile = getPath + ".jsp";
    
%>

<!DOCTYPE html>
<html>
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

    <body>
        <!-- ============================================================== -->
        <!-- main wrapper -->
        <!-- ============================================================== -->
        <div class="dashboard-main-wrapper">
            <!-- ============================================================== -->
            <!-- Header -->
            <!-- ============================================================== -->     
            <%@ include file = "header.jsp" %>
            <!-- ============================================================== -->
            <!-- End header -->
            <!-- ============================================================== -->     
            <%--<%@ include file = "%{getPath}.jsp" %>--%>
            <jsp:include page="<%= theFile %>"/>

            <!-- ============================================================== -->
            <!-- footer -->
            <!-- ============================================================== -->          

            <!-- ============================================================== -->
            <!-- end footer -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- end wrapper  -->
            <!-- ============================================================== -->
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
        <script src="asset/js/bootstrap.bundle.min.js"></script>

</body>
</html>
