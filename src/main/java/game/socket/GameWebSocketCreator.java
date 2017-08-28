package game.socket;

import game.interfaces.GameSocketService;
import interfaces.AccountService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class GameWebSocketCreator implements WebSocketCreator{

    private final AccountService accountService;
    private final GameSocketService gameSocketService;

    public GameWebSocketCreator(GameSocketService gameSocketService, AccountService accountService){
        this.accountService = accountService;
        this.gameSocketService = gameSocketService;
    }


    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        String sessionId = servletUpgradeRequest.getSession().getId();
        String userName = accountService.getUserName(sessionId);
        return new GameWebSocket(userName,gameSocketService);
    }
}
