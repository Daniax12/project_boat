
<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="utilities.*"%>
<%@page import="java.sql.*"%>
<%
    Escale escale = (Escale) request.getAttribute("escale");
    List<Prestation_escale> my_prestations = (List<Prestation_escale>) request.getAttribute("my_prestations");
    Facture facture = (Facture) request.getAttribute("facture");
%>
        <!-- MAIN CONTENT-->
        <div class="main-content">
            <div class="section__content section__content--p30">
                <div class="container-fluid">
                    <div class="d-flex flex-row" style="margin-bottom: 30px;">
                        <h3 style="margin-right: 20%"> ESCALE : <%= escale.getId_escale() %> </h3>
                        <h3> FACTURE :  </h3>
                    </div>
                     
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-body d-flex flex-row">
                                    <div class="col-lg-9">
                                        <table class="table table-borderless">
                                            <tr>
                                                <td style="width: 20%"> Bateau : </td>
                                                <td> <%= escale.escale_boat(null).getBoat_name() %> </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 20%"> Arrive le : </td>
                                                <td> <%= escale.getDebut_prevision() %>  </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div class="col-lg-3">
                                        <div> 
                                            <h3> STATUS FACTURE </h3>
                                        </div>
                                        <div style="margin-top: 20px"> 
                                            <h4 style="color: #4ae"> <%= facture.get_facture_etat() %> </h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 
                    <div class="d-flex flex-row align-content-center" style="margin-bottom: 20px">
                        <h3 style="margin-right: 70%; "> Details facture </h3>
                        <a href="Validate_invoice_ctrl?facture_id=<%= facture.getId_facture() %>&&escale_id=<%= escale.getId_escale() %>">
                            <button class="btn btn-lg btn-success"> VALIDER </button>
                        </a>
                    </div>
                    
                    <div class="row justify-content-center">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="">
                                        <table class="table table-borderless table-striped">
                                            <thead>
                                                <tr>
                                                    <th> Designation </th>
                                                    <th> Quai </th>
                                                    <th> Heure debut </th>
                                                    <th> Heure fin </th>
                                                    <th> Montant </th>
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
                                                        <td> <%= pe.getEnd_prestation()  %> </td>
                                                        <td> Ar <%= pe.get_price_with_exchange(null)  %></td>
                                                    </tr>
                                                <% } } %>
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <td colspan="4" class="text-right"> Total montant: </td>
                                                    <td> AR <%= facture.get_total_montant_facture(null) %> </td>
                                                </tr>
                                            </tfoot>
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
                                <p>Copyright © 2018 Colorlib. All rights reserved. Template by <a href="https://colorlib.com">Colorlib</a>.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

