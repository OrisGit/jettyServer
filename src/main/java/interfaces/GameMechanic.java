package interfaces;

import services.UserProfile;

public interface GameMechanic {
    void run();
    void addUser(String userName);
    void increment(String userName);
}
