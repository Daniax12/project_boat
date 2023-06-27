
<%@page import="model.Utilisateur"%>
<%
   Utilisateur user = (Utilisateur) session.getAttribute("user");
   String error = (String) request.getAttribute("error");
   
%>


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
                            <a href="Home_ctrl">
                                <i class="far fa-check-square"></i> Nouvelle escale
                            </a>
                        </li>
                        <li>
                            <a href="home.jsp?page=list_escale">
                                <i class="far fa-check-square"></i> Liste des escales
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
                    <div class="container-fluid d-flex flex-row justify-content-between">
                        <div class="header-wrap">
                           <h5> Connexion: <%= user.getName_utilisateur() %> </h5>
                        </div>   
                            <% if(error != null){ %>
                            <div style="color: red">
                                   <%= error%>
                            </div> 
                               <% } %>
                        <div class="header-wrap">
                            <a href="Log_out_ctrl">
                                Deconnexion 
                            </a>
                        </div>
                    </div>
                </div>
            </header>
            <!-- HEADER DESKTOP-->


