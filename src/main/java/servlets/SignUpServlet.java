package servlets;

import interfaces.AccountService;
import services.AccountServiceImpl;
import services.UserProfile;
import templater.Templater;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@WebServlet(name = "SignUp", urlPatterns = {"/sigup"})
public class SignUpServlet extends HttpServlet {
    private AccountService accountService;

    public SignUpServlet(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=utf-8");

        UserProfile userProfile = new UserProfile(login,password,email);
        Map<String, Object> data = new HashMap<String,Object>();
        if(accountService.addUser(login,userProfile)){
            data.put("status","Регистрация прошла успешно");
        } else {
            data.put("status","К сожалению такой пользователь уже зарегистрирован");
        }

        resp.getWriter().append(Templater.getPage("status.html",data));
    }
}
