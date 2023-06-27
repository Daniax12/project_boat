<%-- 
    Document   : main.jsp
    Created on : Mar 15, 2023, 9:54:30 PM
    Author     : rango;
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="utilities.*"%>
<%@page import="database.ConnectionBase"%>
<%@page import="mapping.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.time.*"%>


<%
        Connection connection = null;
        ConnectionBase cb = new ConnectionBase();
        
        try {
           connection = cb.dbConnect();
           
                   Prestation_escale pe = new Prestation_escale();
           pe.setId_prestation_escale("PRE_ESC_7");
           pe = BddObject.findById("prestation_escale", pe, null);
           //out.println("Tarif is " + pe.get_price_with_exchange(null));
             Timestamp timestamp = Timestamp.valueOf("2023-06-26 09:50:00");
            int minutesToAdd = 30;

            Timestamp updatedTimestamp = DateUtil.add_minutes_to_timestamp(timestamp, minutesToAdd);
            out.println("Updated Timestamp: " + updatedTimestamp);
            
            
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            out.print(e.fillInStackTrace());
            
        } finally{
            connection.close();
        }

%>
