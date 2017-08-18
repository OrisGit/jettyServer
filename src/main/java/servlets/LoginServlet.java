package servlets;

import templater.Templater;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("login","");
        data.put("password","");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("utf-8");
        resp.getWriter().print(Templater.getPage("login.html",data));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("login", req.getParameter("login"));
        data.put("password",req.getParameter("password"));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("utf-8");
        resp.getWriter().print(Templater.getPage("login.html",data));
    }
}
