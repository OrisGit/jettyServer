package game.web_services;

import game.interfaces.GameMechanic;
import game.interfaces.GameSocketService;
import interfaces.AccountService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class GameWebSocketCreator implements WebSocketCreator{

    private final AccountService accountService;
    private final GameSocketService gameSocketService;
    private final GameMechanic gameMechanic;

    public GameWebSocketCreator(GameSocketService gameSocketService, AccountService accountService, GameMechanic gameMechanic){
        this.accountService = accountService;
        this.gameSocketService = gameSocketService;
        this.gameMechanic = gameMechanic;
    }


    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        String sessionId = servletUpgradeRequest.getSession().getAttribute("uid").toString();
        String userName = accountService.getUserName(sessionId);
        return new GameWebSocket(userName,gameSocketService, gameMechanic);
    }
}
