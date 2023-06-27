<!DOCTYPE html>

<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%
    List<Boat> boats = (List<Boat>) request.getAttribute("boats");
    List<Dock> docks = (List<Dock>) request.getAttribute("docks");
    String error = (String) request.getAttribute("error");
%>



<!-- MAIN CONTENT-->
<div class="main-content">
    <div class="section__content section__content--p30">
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-lg-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="card-title" style="margin-bottom: 50px;">
                                <h3 class="text-center title-2"> Nouvelle escale </h3>
                            </div>
                            <form action="New_escale_ctrl" method="get" novalidate="novalidate">
                                <div class="row form-group">
                                    <div class="col col-md-3">
                                        <label for="selectSm" class=" form-control-label">Choisir bateau :</label>
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


                                <div class="row form-group">
                                    <div class="col col-md-3">
                                        <label for="selectSm" class=" form-control-label"> Date arrive : </label>
                                    </div>
                                    <div class="col-12 col-md-9">
                                        <input id="cc-pament" name="arrive" type="datetime-local" class="form-control" aria-required="true" aria-invalid="false">
                                    </div>
                                </div>
                                       
                                <div class="row form-group">
                                    <div class="col col-md-3">
                                        <label for="selectSm" class=" form-control-label">Chosir quai :</label>
                                    </div>
                                    <div class="col-12 col-md-9">
                                        <select name="dock_id" id="SelectLm" class="form-control-sm form-control">
                                            <% for(Dock dock : docks){ %>
                                                <option value="<%= dock.getId_dock() %>">
                                                    <%= dock.getName_dock() %>
                                                </option>
                                            <% } %>
                                        </select>
                                    </div>
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
                        <p>Copyright © 2018 Colorlib. All rights reserved. Template by <a href="https://colorlib.com">Colorlib</a>.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
                                        </div></div>
                                     
        