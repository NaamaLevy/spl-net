package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;

public class DataBase<T> {
    //fields
    private ConcurrentHashMap<Integer, ConcurrentHashMap<String, Integer>> subscribersMap;
    private ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> topicsMap;
    private ConcurrentHashMap<Integer, ConnectionHandler<T>> clientsMap;
    private ConcurrentHashMap<Integer, User> userMap;
    int nextid;

    private static class SingletonHolder {
        private static DataBase dataBase = new DataBase();
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
}

