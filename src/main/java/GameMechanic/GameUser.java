package GameMechanic;

public class GameUser {
    private String userName;
    private long counter;

    public GameUser(String userName){
        counter=0;
    }

    public void incrementMyCounter(){
        counter++;
    }

    public long getCounter() {
        return counter;
    }

    public String getUserName() {
        return userName;
    }
}
