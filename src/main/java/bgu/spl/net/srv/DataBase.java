package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBase<T> {
    //fields
    private ConcurrentHashMap<Integer, ConcurrentHashMap<String, Integer>> subscribersMap;
    private ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> topicsMap;
    private ConcurrentHashMap<Integer, ConnectionHandler<T>> clientsMap;
    private ConcurrentHashMap<Integer, User> userMap;
    AtomicInteger nextid;

    private static class SingletonHolder {
        private static DataBase dataBase = new DataBase();
    }

    private DataBase(){
        nextid = new AtomicInteger(0);
        subscribersMap = new ConcurrentHashMap<>();
        topicsMap = new ConcurrentHashMap<>();
        clientsMap = new ConcurrentHashMap<>();
        userMap = new ConcurrentHashMap<>();
    }

    public static DataBase getInstance(){ return SingletonHolder.dataBase;}

    public ConcurrentHashMap<Integer, ConcurrentHashMap<String, Integer>> getSubscribersMap() {
        return subscribersMap;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> getTopicsMap() {
        return topicsMap;
    }

    public ConcurrentHashMap<Integer, ConnectionHandler<T>> getClientsMap() {
        return clientsMap;
    }

    public ConcurrentHashMap<Integer, User> getUserMap() {
        return userMap;
    }
    public synchronized void addUser(String userName, String password){
        int id = nextid.get();
        User newUser = new User(userName, password, id);
        nextid.incrementAndGet();
    }
    public int isUserExist(String userName){
        for (User user : userMap.values()){
            if (user.getUserName() == userName)
                return user.getId();
        }
        return -1;
    }
    public boolean isUserLoggedIn(int id){
        if (clientsMap.containsKey(id)){
                return true;
        }
        return false;
    }
}

