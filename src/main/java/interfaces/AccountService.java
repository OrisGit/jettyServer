package interfaces;

import services.UserProfile;

public interface AccountService {

    boolean addUser(String userName, UserProfile userProfile);
    void addSession(String sessionId, UserProfile userProfile);
    UserProfile getUser(String userName);
    UserProfile getSession(String sessionId);
    long getNextUserID();
    void closeSession(String sessionId);
    String getUserName(String sessionId);

}
