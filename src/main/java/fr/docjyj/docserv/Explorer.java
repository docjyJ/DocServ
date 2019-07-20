package fr.docjyj.docserv;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/explorer/*")
@MultipartConfig(fileSizeThreshold=1024*1024, maxFileSize=1024*1024*100, maxRequestSize=1024*1024*100*2)
public class Explorer extends HttpServlet {
	private Methode M = new Methode();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameterMap().containsKey("files")) M.getFiles(request, response);
    	else if(request.getParameterMap().containsKey("download")) M.downloadFile(request, response);
		else this.getServletContext().getRequestDispatcher("/WEB-INF/app/explorer/main.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String path = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
		if(request.getParameterMap().containsKey("upload")) M.uplodeFile(path, request.getParts());
		else if(request.getParameterMap().containsKey("newfile")) M.createFile(path, request.getParameter("title"), request.getParameter("radio"));
		else if(request.getParameterMap().containsKey("delete")) M.recursifDelete(new File(M.location() + path));
		else if(request.getParameterMap().containsKey("edit")) M.edit(path, request.getParameter("code"));
		else if(request.getParameterMap().containsKey("extract")) M.extractZip(path);
    }
}



