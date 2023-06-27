<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="utilities.*"%>
<%@page import="java.sql.*"%>
<%
    Escale escale = (Escale) request.getAttribute("escale");
    List<Prestation_escale> my_prestations = (List<Prestation_escale>) request.getAttribute("my_prestations");
    List<Prestation> prestations = (List<Prestation>) request.getAttribute("prestations");
    Dock dock = escale.get_last_dock(null);
    List<Dock> docks = (List<Dock>) request.getAttribute("docks");
    
%>

        <!-- MAIN CONTENT-->
        <div class="main-content">
            <div class="section__content section__content--p30">
                <div class="container-fluid">
                     <h3 style="margin-bottom: 30px;"> ESCALE: <%= escale.getId_escale() %> </h3>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-body d-flex flex-row">
                                    <div class="col-lg-9">
                                        <table class="table table-borderless">
                                            <tr>
                                                <td style="width: 20%"> Bateau : </td>
                                                <td> <%= escale.escale_boat(null).getBoat_name() %> (<%= escale.escale_boat(null).my_nationality() %>) </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 20%"> Arrive le : </td>
                                                <td> <%= escale.getDebut_prevision() %> </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div class="col-lg-3">
                                        <div> 
                                            <a data-bs-toggle="modal" data-bs-target="#staticBackdropAddPrestation">
                                                <button class="btn btn-primary">
                                                    Ajouter prestation
                                                </button>                              
                                            </a>
                                        </div>
                                        <div style="margin-top: 20px" class="d-flex flex-row"> 
                                            <a href="Facture_ctrl?escale_id=<%= escale.getId_escale() %>">
                                                <button class="btn btn-primary"> Facturer </button>
                                            </a>
                                                <a style="margin-left: 10px" data-bs-toggle="modal" data-bs-target="#staticBackdropChangeDock">
                                                <button class="btn btn-primary">
                                                    Changer quai
                                                </button>                              
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 
                    <h3 style="margin-bottom: 30px;"> Prestations de l'escale </h3>
                    <div class="row justify-content-center">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="">
                                        <table class="table table-borderless table-striped">
                                            <thead>
                                                <tr>
                                                    <th> Nature </th>
                                                    <th> Quai </th>
                                                    <th> Heure debut </th>
                                                    <th> Heure fin </th>
                                                    <th> Status validation </th>
                                                    <th> Action  </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%              
                                                if(my_prestations != null){
                                                    for(int k = 0; k < my_prestations.size(); k++){
                                                     Prestation_escale pe = my_prestations.get(k);
                                                    %>
                                                    <tr>
                                                        <td> <%= pe.pe_prestation(null).getName_prestation() %> </td>
                                                        <td> <%= pe.pe_dock(null).getName_dock() %> </td>
                                                        <td> <%= pe.getDebut_prestation() %></td>
                                                        <%
                                                            if(pe.getEnd_prestation() == null){ %>
                                                            <td> 
                                                                <a data-bs-toggle="modal" data-bs-target="#staticBackdropEndPrestation<%= pe.getId_prestation_escale() %>">
                                                                    <button class="btn btn-secondary">
                                                                        Terminer
                                                                    </button>                              
                                                                </a>
                                                            </td>
                                                            <% } else {  %>
                                                                <td> <%= pe.getEnd_prestation()  %> </td>
                                                            <% } %>
                                                        
                                                        <td> <i class="<%= pe.get_font_etat() %>"></i> </td>
                                                        <td>
                                                            <a href="Validate_prestation_ctrl?id_pe=<%= pe.getId_prestation_escale() %>&&escale_id=<%= escale.getId_escale() %>">
                                                                <button class ="btn btn-primary"> VALIDER </button>  
                                                            </a>
                                                        </td>
                                                    </tr>
                                                <% } } %>
                                            </tbody>
                                        </table>
                                    </div>
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
                                            
            <% for(int i = 0; i < my_prestations.size(); i++){ 
                Prestation_escale pe = my_prestations.get(i);
             %>
                 <!-- ENDING PRESTATION TO ESCALE / MODAL --> 
                <div class="modal fade" id="staticBackdropEndPrestation<%= pe.getId_prestation_escale() %>" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title text-center" id="staticBackdropLabel">
                                    Terminer la prestation  <%-- Modal header : Getting the specific product --%>
                                </h3>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">x</button>  <%-- Closing button the modal --%>
                            </div>

                            <div class="modal-body text-center">
                                <form action="End_prestation_ctrl" method="get" enctype="multipart/form-data">        
                                     <div class="row form-group">
                                        <div class="col col-md-5">
                                            <label for="selectSm" class=" form-control-label"> Heure fin : </label>
                                        </div>
                                        <div class="col-12 col-md-7">
                                            <input id="cc-pament" name="end_prestation" type="datetime-local" class="form-control" aria-required="true" aria-invalid="false">
                                        </div>
                                    </div>
                                    <input type="hidden" name="old_dock" value="<%= dock.getId_dock() %>">
                                    <input type="hidden" name="escale_id" value="<%= escale.getId_escale() %>">
                                    <input type="hidden" name="pe_id" value="<%= pe.getId_prestation_escale() %>">
                                    <input type="submit" value="Enregistrer" class="btn btn-primary">
                                </form>
                            </div>
                            <div class="modal-footer justify-content-center">
                                <%-- IF THERE IS FOOTER --%>
                            </div>
                        </div>
                    </div>
                </div>
             <% } %>
             
             <!-- CHANGING QUAI  -->
                    
            <!-- ADDING NEW PRESTATION TO ESCALE / MODAL --> 
            <div class="modal fade" id="staticBackdropChangeDock" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3 class="modal-title text-center" id="staticBackdropLabel">
                                Changer de quai  <%-- Modal header : Getting the specific product --%>
                            </h3>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">x</button>  <%-- Closing button the modal --%>
                        </div>

                        <div class="modal-body text-center">
                            <form action="Change_dock_ctrl" method="get" enctype="multipart/form-data">
                                <%-- CHOIX PRESTATION --%>
                                <div class="row form-group">
                                    <div class="col col-md-5">
                                        <label for="selectSm" class=" form-control-label">Chosir Quai :</label>
                                    </div>
                                    <div class="col-12 col-md-7">
                                        <select name="new_dock_id" id="SelectLm" class="form-control-sm form-control">
                                            <% for(Dock d : docks){ %>
                                                <option value="<%= d.getId_dock() %>">
                                                   <%= d.getName_dock() %>
                                                </option>
                                            <% } %>
                                        </select>
                                    </div>
                                </div>
                                        
                                 <div class="row form-group">
                                    <div class="col col-md-5">
                                        <label for="selectSm" class=" form-control-label"> Heure debut : </label>
                                    </div>
                                    <div class="col-12 col-md-7">
                                        <input id="cc-pament" name="debut_hour" type="datetime-local" class="form-control" aria-required="true" aria-invalid="false">
                                    </div>
                                </div>
                                <input type="hidden" name="escale_id" value="<%= escale.getId_escale() %>">
                                <input type="hidden" name="dock_id" value="<%= dock.getId_dock() %>">
                                 <input type="submit" value="Enregistrer" class="btn btn-primary">
                            </form>
                        </div>
                        <div class="modal-footer justify-content-center">
                            <%-- IF THERE IS FOOTER --%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
