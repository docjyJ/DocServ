package fr.docjyj.docserv;

import net.kronos.rkon.core.ex.AuthenticationException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class Minecraft extends HttpServlet {
	private Methode M = new Methode();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterMap().containsKey("deconextion")) {
	        request.getSession().invalidate();
	        response.sendRedirect("/");
		}
		else if (request.getParameterMap().containsKey("backup")) {
			M.doBackup();
    	}
		else if (request.getParameterMap().containsKey("serveurs")) {
			M.viewServ(request.getSession().getServletContext());
			this.getServletContext().getRequestDispatcher("/WEB-INF/app/minecraft/servers.jsp").forward(request, response);
		}
		else if (request.getParameterMap().containsKey("serveursJson")) {
			request.getSession().getServletContext().setAttribute("json", M.viewServJson());
			this.getServletContext().getRequestDispatcher("/WEB-INF/app/json.jsp").forward(request, response);
		}
		else {
			this.getServletContext().getRequestDispatcher("/WEB-INF/app/minecraft/main.jsp").forward(request, response);
		}
	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameterMap().containsKey("start")) {
			try {
				M.startServ(request.getParameter("name"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (request.getParameterMap().containsKey("command")) {
			try {
				M.runCMD(request.getParameter("cmd"));
			} catch (AuthenticationException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
