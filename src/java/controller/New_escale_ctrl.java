/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author rango
 */
import java.sql.Timestamp;
import mapping.BddObject;
import model.Escale;
import model.Utilisateur;
import utilities.DateUtil;
import utilities.Ordering;
@WebServlet(name = "New_escale_ctrl", urlPatterns = {"/New_escale_ctrl"})
public class New_escale_ctrl extends HttpServlet {
    private int sequence = 0;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            String arrive = request.getParameter("arrive");
            String depart = request.getParameter("depart");
            String id_boat = request.getParameter("boat_id");
            try {
               Timestamp time = DateUtil.string_to_timestamp(arrive);
            
                out.println(arrive);
                out.println(time);
                out.println(id_boat);
            } catch (Exception e) {
                out.println("Error leleh");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        String arrive = request.getParameter("arrive");
        String id_boat = request.getParameter("boat_id");
        String id_dock = request.getParameter("dock_id");

        try {
            Escale escale = new Escale(id_boat, arrive);
            escale.insert_first_escale(id_dock, arrive, user, null);
        //    out.println("mety leleh");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
          //  out.println("Error leleh");
        } finally{
            RequestDispatcher dispatcher = request.getRequestDispatcher("Home_ctrl");
            dispatcher.forward(request, response);
        }   
        

        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
