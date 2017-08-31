package game.game_mechanic;

import game.interfaces.GameMechanic;
import game.interfaces.GameSocketService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameMechanicImpl implements GameMechanic{
    private static final int SLEEP_TIME = 100;
    private static final int SESSION_TIME = 60 * 1000;

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
        String firstPlayer = session.getFirstName();
        String secondPlayer = session.getSecondName();

        gameSocketService.notifySocketGameOver(firstPlayer,firstWin);
        gameSocketService.notifySocketGameOver(secondPlayer,!firstWin);

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
    public void incrementScore(String userName) {
        GameSession gameSession = nameToGameSession.get(userName);
        gameSession.incrementUser(userName);
        String enemy = gameSession.getEnemy(userName);
        long score = gameSession.getScore(userName);

        gameSocketService.notifySocketUserIncrementScore(userName,score);
        gameSocketService.notifySocketEnemyIncrementScore(enemy,score);
    }

    private void startGame(String userName){
        GameSession gameSession = new GameSession(userName, waitingUser);
        gameSessions.add(gameSession);
        nameToGameSession.put(userName,gameSession);
        nameToGameSession.put(waitingUser, gameSession);

        gameSocketService.notifySocketStartGame(userName,waitingUser);
        gameSocketService.notifySocketStartGame(waitingUser,userName);
    }
}
