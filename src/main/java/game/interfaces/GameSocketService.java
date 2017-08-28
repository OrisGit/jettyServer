package game.interfaces;

import game.socket.GameWebSocket;

public interface GameSocketService {
    void notifySocketUserIncrementScore(String user, long score);
    void notifySocketEnemyIncrementScore(String user, long score);
    void notifySocketStartGame(String user, String enemy);
    void notifySocketGameOver(String user, boolean win);
    void addUser(GameWebSocket user);
    void notifyGameMechanicUserIncrementScore(String user);
}
