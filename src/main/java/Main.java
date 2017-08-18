import servlets.LoginServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args){
        Server server = new Server(8080);

        LoginServlet loginServlet = new LoginServlet();
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(loginServlet), "/login");
        server.setHandler(handler);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
