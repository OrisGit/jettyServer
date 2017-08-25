package interfaces;

public interface GameSocketService {
    void notifyUserIncrement(String user);
    void notifyEnemyIncrement(String enemy);
    void notifyStartGame(String user);
    void notifyGameOver(String user, boolean win);
    void addUser(GameWebSocket user);
}
