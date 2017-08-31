package game.web_services;

import game.interfaces.GameMechanic;
import game.interfaces.GameSocketService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameSocketServiceImpl implements GameSocketService {

    private Map<String, GameWebSocket> nameToSocket  = new ConcurrentHashMap<String, GameWebSocket>();

    @Override
    public void notifySocketUserIncrementScore(String user, long score) {
        nameToSocket.get(user).userIncrementScore(score);
    }

    @Override
    public void notifySocketEnemyIncrementScore(String user, long score) {
        nameToSocket.get(user).enemyIncrementScore(score);
    }

    @Override
    public void notifySocketStartGame(String user, String enemy) {
        nameToSocket.get(user).startGame(enemy);
    }

    @Override
    public void notifySocketGameOver(String user, boolean win) {
        nameToSocket.get(user).gameOver(win);
    }

    @Override
    public void addUser(GameWebSocket user) {
        String userName = user.getUserName();
        nameToSocket.put(userName, user);
    }
}