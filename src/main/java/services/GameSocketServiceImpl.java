package services;

import interfaces.GameSocketService;

import java.util.Map;

public class GameSocketServiceImpl implements GameSocketService{

    Map<String, GameWebSocket> nameToSocket;

    @Override
    public void notifyUserIncrement(String user) {

    }

    @Override
    public void notifyEnemyIncrement(String enemy) {

    }

    @Override
    public void notifyStartGame(String user) {

    }

    @Override
    public void notifyGameOver(String user, boolean win) {

    }

    @Override
    public void addUser(GameWebSocket user) {

    }
}
