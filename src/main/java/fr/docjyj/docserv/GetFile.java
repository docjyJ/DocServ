package fr.docjyj.docserv;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/file/*")
public class GetFile extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new Methode().getFiles(request, response, new File(request.getServletContext().getRealPath("/") + "WEB-INF/file/" + URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8")));
    }
}