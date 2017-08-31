package game.web_services;

import game.interfaces.GameMechanic;
import game.interfaces.GameSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;

import java.io.IOException;

@WebSocket
public class GameWebSocket{

    private Session session;
    private final String userName;
    private final GameSocketService gameSocketService;
    private final GameMechanic gameMechanic;


    public GameWebSocket(String userName, GameSocketService gameSocketService, GameMechanic gameMechanic){
        this.gameSocketService = gameSocketService;
        this.userName = userName;
        this.gameMechanic = gameMechanic;
    }

    @OnWebSocketMessage
    public void onMessage(String message){
        gameMechanic.incrementScore(userName);
    }

    @OnWebSocketConnect
    public void onConnect(Session session){
        this.session = session;
        gameSocketService.addUser(this);
        gameMechanic.addUser(userName);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        //TODO Удаление пользователя из gameSocektService, gameMechanic и если он в сессии то закрытие сессии и отпрака сообщения пользователям
    }

    public String getUserName() {
        return userName;
    }

    public void enemyIncrementScore(long score){
        JSONObject enemyIncScore = new JSONObject();
        enemyIncScore.put("status","enemy_inc");
        enemyIncScore.put("score",score);
        try {
            session.getRemote().sendString(enemyIncScore.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userIncrementScore(long score){
        JSONObject userIncScore = new JSONObject();
        userIncScore.put("status","user_inc");
        userIncScore.put("score",score);
        try {
            session.getRemote().sendString(userIncScore.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame(String enemy){
        JSONObject startGame = new JSONObject();
        startGame.put("status","start");
        startGame.put("enemy",enemy);
        try {
            session.getRemote().sendString(startGame.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gameOver(boolean win){
        JSONObject gameOver = new JSONObject();
        gameOver.put("status","end");
        gameOver.put("win",win);
        try {
            session.getRemote().sendString(gameOver.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
