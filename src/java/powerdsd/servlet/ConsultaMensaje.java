/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package powerdsd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import powerdsd.jms.ConsultaColas;

/**
 *
 * @author s7263
 */
@WebServlet(name = "ConsultaMensaje", urlPatterns = {"/ConsultaMensaje"})
public class ConsultaMensaje extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
            HttpSession sesion = request.getSession();


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        if (request.getParameter("consultar") != null) {

            ConsultaColas consultaColas = new ConsultaColas();



            if (sesion.getAttribute("mensajes") == null) {
                sesion.setAttribute("mensajes", new ArrayList<String>());
            }
            
            List<String> mensajes =(List<String>)sesion.getAttribute("mensajes");

            


            try {
                if (consultaColas.numeroMensajes() > 0) {
                    StringBuilder mensaje = new StringBuilder();
                    mensaje.append(dateFormat.format(new Date())).append(" - ");
                    mensaje.append(consultaColas.obtener());
                    
                    mensajes.add(mensaje.toString());
                }
            } catch (NamingException ex) {
                Logger.getLogger(ConsultaMensaje.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JMSException ex) {
                Logger.getLogger(ConsultaMensaje.class.getName()).log(Level.SEVERE, null, ex);
            }





        } else if (request.getParameter("reset") != null) {
            sesion.invalidate();
        }
        
        getServletContext().getRequestDispatcher("/consultar.jsp").forward(request, response);
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
