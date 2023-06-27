<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="mapping.BddObject"%>
<%@page import="java.util.*"%>
<%@page import="utilities.*"%>
<%@page import="java.sql.*"%>
<%
    List<Escale> escales = BddObject.findByOrder("escale", new Escale(), "debut_prevision", Ordering.DESC, null);
%>


        <!-- MAIN CONTENT-->
        <div class="main-content">
            <div class="section__content section__content--p30">
                <div class="container-fluid">
                    <div class="card-title" style="margin-bottom: 50px;">
                         <h3 class="title-2"> Liste des escales </h3>
                     </div>
                        <h4 style="margin-bottom: 20px"> </h4>
                        <div class="row justify-content-center">
                            <div class="col-lg-12">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="">
                                            <table class="table table-striped table-striped">
                                                <thead>
                                                    <tr>
                                                        <th> Escale </th>
                                                        <th> Bateau </th>
                                                        <th> Arrive le  </th>
                                                        <th> Quai  </th>
                                                        <th> Action  </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% 
                                                    if(escales != null){
                                                        for(int k = 0; k < escales.size(); k++){
                                                        Escale escale = escales.get(k); %>
                                                        <tr>
                                                            <td> <%= escale.getId_escale()  %> </td>
                                                            <td> <%= escale.escale_boat(null).getBoat_name() %> </td>
                                                            <td> <%= escale.getDebut_prevision()  %> </td>
                                                            <td> <%= escale.get_last_dock(null).getName_dock()  %> </td>
                                                            <td>  
                                                                <a href="Detail_escale_ctrl?escale_id=<%= escale.getId_escale() %>">
                                                                    <button class ="btn btn-primary"> Detail </button>  
                                                                </a>
                                                            </td>
                                                        </tr>
                                                        <% } 
                                                    } %>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                     <% // } %>
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
