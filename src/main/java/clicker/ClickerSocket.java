package clicker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class ClickerSocket {
    private static final Logger logger = LogManager.getLogger(ClickerSocket.class);
    static int counter = 0;
    private static Set<ClickerSocket> users = Collections.newSetFromMap(new ConcurrentHashMap<ClickerSocket, Boolean>());
    private Session session;

    public ClickerSocket(){
    }

    @OnWebSocketConnect
    public void OnConnect(Session session){
        logger.info("Соединение создано");
        this.session = session;
        users.add(this);
    }

    @OnWebSocketClose
    public void OnClose(int statusCode, String reason){
        logger.info("Соединение закрыто");
        users.remove(this);
    }

    @OnWebSocketMessage
    public void OnMessage(String data){
        logger.info("Сообщение: "+data);
        counter++;
        for(ClickerSocket user : users){
            try {
                user.getSession().getRemote().sendString(String.valueOf(counter));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
