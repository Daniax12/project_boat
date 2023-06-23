/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.RequestDispatcher;
import model.Facture;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import mapping.BddObject;
import model.Escale;
import model.Prestation;
import model.Prestation_escale;
import model.Utilisateur;
import utilities.Ordering;

/**
 *
 * @author rango
 */
@WebServlet(name = "Facture_ctrl", urlPatterns = {"/Facture_ctrl"})
public class Facture_ctrl extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Facture_ctrl</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Facture_ctrl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        //processRequest(request, response);
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        String escale_id = request.getParameter("escale_id");
        
        try {
            Escale escale  = new Escale();
            escale.setId_escale(escale_id);
            escale = BddObject.findById("escale", escale, null);  // PRENDRE L'ESCALE
            
            Facture escale_facture = escale.escale_facture(null);
            
            if(escale_facture == null){
                escale_facture = Facture.create_facture(user, escale_id, null);
            }
           
           
            Prestation_escale pe = new Prestation_escale();                         // PRENDRE TOUS LES PROPOSITIONS DE L'ESCALE
            pe.setId_escale(escale_id);
            List<Prestation_escale> escale_prestation = BddObject.findByOrder("prestation_escale", pe, "debut_prestation", Ordering.DESC, null);
          
            // send all data
            request.setAttribute("escale", escale);
            request.setAttribute("facture", escale_facture);
            request.setAttribute("my_prestations", escale_prestation);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("Detail_escale_ctrl?escale_id="+escale_id);
            dispatcher.forward(request, response);
        } finally{
            RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp?page=facture");
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
