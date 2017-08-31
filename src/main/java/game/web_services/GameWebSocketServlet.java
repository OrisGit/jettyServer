package game.web_services;

import game.interfaces.GameMechanic;
import game.interfaces.GameSocketService;
import interfaces.AccountService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class GameWebSocketServlet extends WebSocketServlet {

    private final GameSocketService gameSocketService;
    private final AccountService accountService;
    private final GameMechanic gameMechanic;

    private static final long IDLE_TIMEOUT = 10 * 60 * 1000;

    public GameWebSocketServlet(GameSocketService gameSocketService, AccountService accountService, GameMechanic gameMechanic){
        this.accountService = accountService;
        this.gameSocketService = gameSocketService;
        this.gameMechanic = gameMechanic;
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(IDLE_TIMEOUT);
        webSocketServletFactory.setCreator(new GameWebSocketCreator(gameSocketService,accountService, gameMechanic));
    }
}
