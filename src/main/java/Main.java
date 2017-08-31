import chat.WebSocketChatServlet;
import clicker.WebSocketClickerServlet;
import game.game_mechanic.GameMechanicImpl;
import game.interfaces.GameMechanic;
import game.interfaces.GameSocketService;
import game.web_services.GameServlet;
import game.web_services.GameSocketServiceImpl;
import game.web_services.GameWebSocketServlet;
import interfaces.AccountService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.AccountServiceImpl;
import servlets.AdminServlet;
import servlets.SignInServlet;
import servlets.SignOutServlet;
import servlets.SignUpServlet;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger("Main");
    public static void main(String[] args){
        //Устанавлеваем порт
        int port = 8080;
        if(args.length==1){
            try{
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                log.log(Level.WARNING,"Введён не верный номер порта. Номер порта это натуральное число " +
                        "в диапзоне от 1 до 65 535.");
                System.exit(1);
            }

        }

        log.log(Level.INFO,"Сервер будет запущен со следующими параметрами:\nПорт: "+port+"\n");


        //Создаём сервлеты и записываем их в хэндлер
        AccountService accountService = new AccountServiceImpl();
        GameSocketService gameSocketService = new GameSocketServiceImpl();
        GameMechanic gameMechanic = new GameMechanicImpl(gameSocketService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        contextHandler.addServlet(new ServletHolder(new SignInServlet(accountService)),"/signin");
        contextHandler.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        contextHandler.addServlet(new ServletHolder(new AdminServlet()), "/admin");
        contextHandler.addServlet(new ServletHolder(new WebSocketChatServlet()),"/chat");
        contextHandler.addServlet(new ServletHolder(new SignOutServlet(accountService)),"/signout");
        contextHandler.addServlet(new ServletHolder(new WebSocketClickerServlet()),"/clicker");
        contextHandler.addServlet(new ServletHolder(new GameServlet(accountService)),"/game");
        contextHandler.addServlet(new ServletHolder(new GameWebSocketServlet(gameSocketService,accountService,gameMechanic)),"/gameplay");

        //Созаём хэндлер для доступа ресурсам в директории
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");
        resourceHandler.setDirectoriesListed(true);

        //Создаём список хэндлеров
        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler,contextHandler});

        //Запускаем сервер
        Server server = new Server(port);
        server.setHandler(handlerList);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        gameMechanic.run();

    }
}
