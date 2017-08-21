package chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.Set;

@WebSocket
public class ChatWebSocket {
    private Set<ChatWebSocket> users;
    private Session session;

    public ChatWebSocket(Set<ChatWebSocket> users){
        this.users = users;
    }

    @OnWebSocketMessage
    public void OnMessage(String data){
        for(ChatWebSocket user : users){
            try{
                user.getSession().getRemote().sendString(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @OnWebSocketConnect
    public void OnConnect(Session session){
        users.add(this);
        setSession(session);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        users.remove(this);
    }

}
