package net.yeah.shiro.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String url = req.getServletPath();
		
		System.out.println("GET, url=" + url);
		
		req.getRequestDispatcher("/login.jsp")
		   .forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		Subject current = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, false);
		
		try {
			current.login(token);
			Session session = current.getSession();
			System.out.println(session.getId());
			System.out.println(session.getHost());
			System.out.println(session.getTimeout());
			resp.sendRedirect("success.jsp");
		} catch (Exception e) {
			req.setAttribute("error", e.toString());
			req.getRequestDispatcher("/login.jsp")
			   .forward(req, resp);
		}
	}

}
