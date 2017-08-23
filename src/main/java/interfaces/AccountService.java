package interfaces;

import services.UserProfile;

public interface AccountService {

    boolean addUser(String userName, UserProfile userProfile);
    void addSession(String sessionId, UserProfile userProfile);
    UserProfile getUser(String userName);
    UserProfile getSession(String sessionId);
    public long getNextUserID();
    public void closeSession(String sessionId);

}
