package com.hitechhealth.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hitechhealth.facade.UserFacade;


@WebServlet(name = "LoginService", urlPatterns = {"/LoginService"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 6143705945613588910L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            try {

                if (request.getMethod().equals("POST")) {
                    
                    UserFacade userFacade = new UserFacade();
                    
                    if(!"".equals(request.getParameter("email"))  && 
                    		!"".equals(request.getParameter("password"))){
                    	
                    	String token = userFacade.authenticatesUser(request.getParameter("email"), request.getParameter("password"));
                    	if(token != null) {
                    		out.print("{\"token\": \"" + token + "\"}");
                    	}else {
                    		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    	}
                    }else {
                    	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    }
                    
                } else {
                	response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                }
            } catch (Exception e) {
            	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print(e.getMessage());
            }
        } finally {
            out.close();
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
