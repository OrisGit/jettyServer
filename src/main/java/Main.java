import chat.WebSocketChatServlet;
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
import servlets.SignUpServlet;

public class Main {
    public static void main(String[] args){

        //Устанавлеваем порт
        int port = 8080;
        if(args.length==1){
            try{
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                System.out.append("Введён не верный номер порта. Номер порта это натуральное число " +
                        "в диапзоне от 1 до 65 535.");
                System.exit(1);
            }

        }

        System.out.append("Сервер будет запущен со следующими параметрами:/nПорт: ").append(String.valueOf(port));


        //Создаём сервлеты и записываем их в хэндлер
        AccountService accountService = new AccountServiceImpl();

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        contextHandler.addServlet(new ServletHolder(new SignInServlet(accountService)),"/signin");
        contextHandler.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        contextHandler.addServlet(new ServletHolder(new AdminServlet()), "/admin");
        contextHandler.addServlet(new ServletHolder(new WebSocketChatServlet()),"/chat");

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
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
