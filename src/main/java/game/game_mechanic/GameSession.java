package game.game_mechanic;

import java.util.Date;

public class GameSession {

    private final long startTime;
    private final GameUser first;
    private final GameUser second;

    public GameSession(String first, String second){
        this.first = new GameUser(first);
        this.second = new GameUser(second);

        this.startTime = new Date().getTime();
    }

    public void incrementUser(String name){
        if(first.getUserName().equals(name)){
            first.incrementMyCounter();
        }else{
            second.incrementMyCounter();
        }
    }

    public boolean getFirstWin(){
        return first.getScore()>second.getScore();

    }

    public String getEnemy(String user){
        return user.equals(getFirstName())?getSecondName():getFirstName();
    }

    public long getTime(){
        return new Date().getTime() - startTime;
    }

    public String getFirstName() {
        return first.getUserName();
    }

    public String getSecondName() {
        return second.getUserName();
    }

    public long getScore(String user){
        return user.equals(first.getUserName())?first.getScore():second.getScore();
    }
}
