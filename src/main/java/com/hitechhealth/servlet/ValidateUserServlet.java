package com.hitechhealth.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hitechhealth.facade.ConfigAppFacade;
import com.hitechhealth.facade.UserFacade;
import com.hitechhealth.vo.ConfigAppVO;

@WebServlet(name = "ValidateUser", urlPatterns = { "/ValidateUser" })
public class ValidateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 6143705945613588910L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {


			if (!"".equals(request.getParameter("k"))) {
				
				UserFacade userFacade = new UserFacade();
				ConfigAppFacade configAppFacade = new ConfigAppFacade();
				ConfigAppVO configAppVO = configAppFacade.getConfigApp();

				out.print(getHtmlPageRegister(userFacade.validateUser(request.getParameter("k")), configAppVO.getUrlClient()));

			} else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				out.print("This link is incomplete. Check the link sent in your email.");
			}

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

	private String getHtmlPageRegister(int isValidate, String url) {
		String linkBack = url;
		return "<!DOCTYPE html>" + "<html>" + "<head>" + "" + "  <meta charset=\"utf-8\">"
				+ "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">" + "  <title>Email Confirmation</title>"
				+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
				+ "  <style type=\"text/css\">" + "  /**"
				+ "   * Google webfonts. Recommended to include the .woff version for cross-client compatibility."
				+ "   */" + "  @media screen {" + "    @font-face {" + "      font-family: 'Source Sans Pro';"
				+ "      font-style: normal;" + "      font-weight: 400;"
				+ "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');"
				+ "    }" + "" + "    @font-face {" + "      font-family: 'Source Sans Pro';"
				+ "      font-style: normal;" + "      font-weight: 700;"
				+ "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');"
				+ "    }" + "  }" + "" + "  /**" + "   * Avoid browser level font resizing." + "   * 1. Windows Mobile"
				+ "   * 2. iOS / OSX" + "   */" + "  body," + "  table," + "  td," + "  a {"
				+ "    -ms-text-size-adjust: 100%; /* 1 */" + "    -webkit-text-size-adjust: 100%; /* 2 */" + "  }" + ""
				+ "  /**" + "   * Remove extra space added to tables and cells in Outlook." + "   */" + "  table,"
				+ "  td {" + "    mso-table-rspace: 0pt;" + "    mso-table-lspace: 0pt;" + "  }" + "" + "  /**"
				+ "   * Better fluid images in Internet Explorer." + "   */" + "  img {"
				+ "    -ms-interpolation-mode: bicubic;" + "  }" + "" + "  /**"
				+ "   * Remove blue links for iOS devices." + "   */" + "  a[x-apple-data-detectors] {"
				+ "    font-family: inherit !important;" + "    font-size: inherit !important;"
				+ "    font-weight: inherit !important;" + "    line-height: inherit !important;"
				+ "    color: inherit !important;" + "    text-decoration: none !important;" + "  }" + "" + "  /**"
				+ "   * Fix centering issues in Android 4.4." + "   */" + "  div[style*=\"margin: 16px 0;\"] {"
				+ "    margin: 0 !important;" + "  }" + "" + "  body {" + "    width: 100% !important;"
				+ "    height: 100% !important;" + "    padding: 0 !important;" + "    margin: 0 !important;" + "  }"
				+ "" + "  /**" + "   * Collapse table borders to avoid space between cells." + "   */" + "  table {"
				+ "    border-collapse: collapse !important;" + "  }" + "" + "  a {" + "    color: #1a82e2;" + "  }"
				+ "" + "  img {" + "    height: auto;" + "    line-height: 100%;" + "    text-decoration: none;"
				+ "    border: 0;" + "    outline: none;" + "  }" + "  </style>" + "" + "</head>"
				+ "<body style=\"background-color: #e9ecef;\">" + "" + "  <!-- start preheader -->"
				+ "  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">"
				+ "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox."
				+ "  </div>" + "  <!-- end preheader -->" + "" + "  <!-- start body -->"
				+ "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">" + "" + "  " + ""
				+ "    <!-- start hero -->" + "    <tr>" + "      <td align=\"center\" bgcolor=\"#e9ecef\">"
				+ "        <!--[if (gte mso 9)|(IE)]>"
				+ "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">"
				+ "        <tr>" + "        <td align=\"center\" valign=\"top\" width=\"600\">" + "        <![endif]-->"
				+ "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"
				+ "          <tr>"
				+ "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">"
				+ "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">"+(isValidate == 1 ? "Thank you for registering!" : "This account is already active!") +"</h1>"
				+ "            </td>" + "          </tr>" + "        </table>" + "        <!--[if (gte mso 9)|(IE)]>"
				+ "        </td>" + "        </tr>" + "        </table>" + "        <![endif]-->" + "      </td>"
				+ "    </tr>" + "    <!-- end hero -->" + "" + "    <!-- start copy block -->" + "    <tr>"
				+ "      <td align=\"center\" bgcolor=\"#e9ecef\">" + "        <!--[if (gte mso 9)|(IE)]>"
				+ "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">"
				+ "        <tr>" + "        <td align=\"center\" valign=\"top\" width=\"600\">" + "        <![endif]-->"
				+ "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"
				+ "" + "         " + "" + "          <!-- start button -->" + "          <tr>"
				+ "            <td align=\"left\" bgcolor=\"#ffffff\">"
				+ "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
				+ "                <tr>"
				+ "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">"
				+ "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
				+ "                      <tr>"
				+ "                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">"
				+ "                          <a href=\"" + linkBack
				+ "\" target=\"_self\" style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\">Access your new account!</a>"
				+ "                        </td>" + "                      </tr>" + "                    </table>"
				+ "                  </td>" + "                </tr>" + "              </table>" + "            </td>"
				+ "          </tr>" + "          <!-- end button -->" + "" + "          " + "" + "        " + ""
				+ "        </table>" + "        <!--[if (gte mso 9)|(IE)]>" + "        </td>" + "        </tr>"
				+ "        </table>" + "        <![endif]-->" + "      </td>" + "    </tr>"
				+ "    <!-- end copy block -->" + "" + "    " + "" + "  </table>" + "  <!-- end body -->" + ""
				+ "</body>" + "</html>";
	}

}
