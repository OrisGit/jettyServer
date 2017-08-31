package game.interfaces;

import game.web_services.GameWebSocket;

public interface GameSocketService {
    void notifySocketUserIncrementScore(String user, long score);
    void notifySocketEnemyIncrementScore(String user, long score);
    void notifySocketStartGame(String user, String enemy);
    void notifySocketGameOver(String user, boolean win);
    void addUser(GameWebSocket user);
}
