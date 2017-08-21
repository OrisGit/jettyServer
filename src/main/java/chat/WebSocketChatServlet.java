package chat;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet{

    private final static int LOGOUT_TIME = 10*60*1000;

    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        webSocketServletFactory.setCreator(new WebSocketChatCreator());
    }
}
