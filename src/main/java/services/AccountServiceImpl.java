package services;

import interfaces.AccountService;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements AccountService {
    Map<String, UserProfile> users = new HashMap<String, UserProfile>();
    Map<String, UserProfile> sessions = new HashMap<String, UserProfile>();

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
}
