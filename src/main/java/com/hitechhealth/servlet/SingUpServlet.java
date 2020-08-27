package com.hitechhealth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hitechhealth.facade.UserFacade;
import com.hitechhealth.vo.UserVO;

@WebServlet(name = "SingUpService", urlPatterns = { "/SingUpService" })
public class SingUpServlet extends HttpServlet {

	private static final long serialVersionUID = 6143705945613588910L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {

			if (request.getMethod().equals("POST")) {

				UserFacade userFacade = new UserFacade();

				if (!"".equals(request.getParameter("name")) && !"".equals(request.getParameter("email"))
						&& !"".equals(request.getParameter("password"))) {

					UserVO userVO = new UserVO();
					userVO.setName(request.getParameter("name"));
					userVO.setEmail(request.getParameter("email"));
					userVO.setPassword(request.getParameter("password"));
					userFacade.save(userVO, "http://"+
					request.getServerName() + ":" + request.getServerPort()  + request.getContextPath() );
					
					response.setStatus(HttpServletResponse.SC_OK);

				} else {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					out.print("Fill in all required information.");
				}

			} else {
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("There is already a user with this email.");
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print(e.getMessage());
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
