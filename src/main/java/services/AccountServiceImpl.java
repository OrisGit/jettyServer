package services;

import interfaces.AccountService;

import java.util.HashMap;
import java.util.Map;

import static services.Debug.DEBUG_MODE;

public class AccountServiceImpl implements AccountService {
    Map<String, UserProfile> users = new HashMap<String, UserProfile>();
    Map<String, UserProfile> sessions = new HashMap<String, UserProfile>();

    private long lastUserId = 0;

    public AccountServiceImpl(){
        if(DEBUG_MODE){
            addUser("test",new UserProfile("test","test",""));
            addUser("test1",new UserProfile("test2","test",""));
        }
    }

    public boolean addUser(String userName, UserProfile userProfile){
        if(users.containsKey(userName))
            return false;
        users.put(userName,userProfile);
        return true;
    }

    public void addSession(String sessionId, UserProfile userProfile){
        sessions.put(sessionId, userProfile);
    }

    public UserProfile getUser(String userName){
        return users.get(userName);
    }

    public UserProfile getSession(String sessionId){
        return sessions.get(sessionId);
    }

    public String getUserName(String sessionId){
        return getSession(sessionId).getLogin();
    }

    public long getNextUserID(){
        return lastUserId++;
    }

    @Override
    public void closeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
