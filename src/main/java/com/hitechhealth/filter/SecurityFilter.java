package com.hitechhealth.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hitechhealth.facade.UserFacade;

@WebFilter("/*")
public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig fc) throws ServletException {
		// throw new UnsupportedOperationException("Not supported yet."); //To change
		// body of generated methods, choose Tools | Templates.
	}

	@Override
	public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) sr;
		HttpServletResponse httpResponse = (HttpServletResponse) sr1;
		PrintWriter out = null;

		try {

			httpResponse.setHeader("Access-Control-Allow-Origin", "*");
			httpResponse.setHeader("Access-Control-Allow-Headers","Authorization, Origin, Content-Type, X-Auth-Token, token");
			httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
			httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
			httpResponse.setHeader("Access-Control-Max-Age", "1209600");

			if (httpRequest.getServletPath().startsWith("/rest")) {

				try {
					
					String isChromeBrowser = httpRequest.getHeader("access-control-request-method");
	                if (isChromeBrowser != null) {
	                    httpResponse.setStatus(HttpServletResponse.SC_OK);
	                }else {
	                	
	                	String token = httpRequest.getHeader("token");
	                	
	                	if (token == null) {
	                		httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
	                	}
	                	
	                	UserFacade userFacade = new UserFacade();
	                	
	                	if (userFacade.isTokenValido(token)) {
	                		fc.doFilter(sr, sr1);
	                	} else {
	                		httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
	                	}
	                }
					
				} catch (Exception e) {
					out = httpResponse.getWriter();
					//httpResponse.setContentType("application/json;charset=UTF-8");
					httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
					out.print("{\"error\": \"" + e.getMessage() + "\"}");
				}
			} else {
				fc.doFilter(sr, sr1);
			}
		} finally {
			if (out != null) {

				out.close();
			}
		}
	}

	@Override
	public void destroy() {
		// throw new UnsupportedOperationException("Not supported yet."); //To change
		// body of generated methods, choose Tools | Templates.
	}
}
