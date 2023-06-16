<%-- 
    Document   : main.jsp
    Created on : Mar 15, 2023, 9:54:30 PM
    Author     : rango;
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Boat"%>
<%@page import="model.Dock"%>
<%@page import="model.Proposition"%>
<%@page import="utilities.*"%>
<%@page import="database.ConnectionBase"%>
<%@page import="mapping.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.NumberFormat"%>


<%
        Connection connection = null;
        ConnectionBase cb = new ConnectionBase();
        
        try {
           connection = cb.dbConnect();
           out.println(connection + "<br>");
        //out.println("Adding "+ DateUtil.time_difference_two_timestamp(debut, main_date));
            
        Dock dock = new Dock();
        dock.setId_dock("QUAI3");
         dock = BddObject.findById(null, dock, connection);
         List<Proposition> all = dock.dock_propositions_escale(connection);
         
         for(int i = 0; i < all.size(); i++){
            out.println("Id escale is " + all.get(i).getEscale_propositin().getId_escale() + "<br>");
            out.println("Attente is "+DateUtil.long_to_string(all.get(i).getAttente_duration()) + "<br>");
             out.println("Debut prevision is "+all.get(i).getEscale_propositin().getDebut_prevision() + "<br>");
             out.println("Proposition debut is "+all.get(i).getDebut_propose() + "<br>");
             out.println("Proposition end is "+all.get(i).getEnd_propose() + "<br>");
             out.println("--------------------------------- <br>");
         }


            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            out.print(e.fillInStackTrace());
            
        } finally{
            connection.close();
        }

%>
