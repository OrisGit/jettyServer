package servlets;

import interfaces.AccountService;
import templater.Templater;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oris on 23.08.2017.
 */
public class SignOutServlet extends HttpServlet {

    private AccountService accountService;

    public SignOutServlet(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> data = new HashMap<String,Object>();
        HttpSession session = req.getSession();
        Long userId = (Long)session.getAttribute("uid");
        if(userId!=null && accountService.getSession(userId.toString())!=null){
            accountService.closeSession(userId.toString());
            req.getSession().removeAttribute("uid");
            data.put("status","Выход выполнен");
        }else{
            data.put("status","Вы не выполнили вход");
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html;charset=utf8");


        resp.getWriter().append(Templater.getPage("status.html",data));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
