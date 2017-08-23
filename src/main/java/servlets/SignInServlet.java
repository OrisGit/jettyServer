package servlets;

import interfaces.AccountService;
import services.AccountServiceImpl;
import services.UserProfile;
import templater.Templater;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignInServlet extends HttpServlet {
    private AccountService accountService;

    public SignInServlet(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Map<String, Object> data = new HashMap<String, Object>();
        UserProfile userProfile = accountService.getUser(login);
        if(userProfile!=null && userProfile.getPassword().equals(password)){
            data.put("SignInStatus", "Вход выполнен успешно");
            HttpSession session = req.getSession();
            Long userID = accountService.getNextUserID();
            session.setAttribute("sessionId",userID);
            accountService.addSession(String.valueOf(userID), userProfile);
        }else{
            data.put("SignInStatus", "Не верное имя пользователя или пароль");
        }



        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=utf-8");

        resp.getWriter().append(Templater.getPage("signinstatus.html",data));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
