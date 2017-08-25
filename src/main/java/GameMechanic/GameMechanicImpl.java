package GameMechanic;

import interfaces.GameMechanic;
import interfaces.GameSocketService;
import org.eclipse.jetty.websocket.api.Session;
import services.UserProfile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameMechanicImpl implements GameMechanic{
    private static final int SLEEP_TIME = 100;
    private static final int SESSION_TIME = 15 * 1000;

    private String waitingUser = null;

    private Map<String, GameSession> nameToGameSession = new HashMap<String, GameSession>();
    private Set<GameSession> gameSessions = new HashSet<GameSession>();
    private GameSocketService gameSocketService;

    public GameMechanicImpl(GameSocketService gameSocketService){
        this.gameSocketService = gameSocketService;
    }

    @Override
    public void run() {
        while (true){
            gameStep();
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void gameStep() {
        for(GameSession session : gameSessions){
            if(session.getTime()>SESSION_TIME){
                gameOver(session);
            }
        }
    }

    private void gameOver(GameSession session){
        boolean firstWin = session.getFirstWin();

        //TODO: Отправляем через Сокет сервис

        nameToGameSession.remove(session.getFirstName());
        nameToGameSession.remove(session.getSecondName());
        gameSessions.remove(session);
    }

    @Override
    public void addUser(String userName) {
        if(waitingUser!=null){
            startGame(userName);
            waitingUser = null;
        }else{
            waitingUser = userName;
        }

    }

    @Override
    public void increment(String userName) {
        GameSession gameSession = nameToGameSession.get(userName);
        gameSession.incrementUser(userName);

        //TODO уведомляем
    }

    private void startGame(String userName){
        GameSession gameSession = new GameSession(userName, waitingUser);
        gameSessions.add(gameSession);
        nameToGameSession.put(userName,gameSession);
        nameToGameSession.put(waitingUser, gameSession);

        //TODO Уведомляем через сокет
    }
}
