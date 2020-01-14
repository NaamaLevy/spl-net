package bgu.spl.net.srv;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class ConnectionsImpl<T> implements Connections<T> {

    //fields
    private DataBase DB;
    private final Object lock;

    private static class SingletonHolder{
        private static ConnectionsImpl connections = new ConnectionsImpl();
    }

    private ConnectionsImpl() {
        DB = DataBase.getInstance();
        lock = new Object();
    }

    public static ConnectionsImpl getInstance(){return SingletonHolder.connections;}

    @Override
    public boolean send(int connectionId, T msg) {
        ConcurrentHashMap<Integer, ConnectionHandler<T>> clientsMap = DB.getClientsMap();
        if (!clientsMap.containsKey(connectionId))
            return false;
        ConnectionHandler ch = clientsMap.get(connectionId);
        //sync to prevent unregistering user while trying sending him a message
        synchronized (ch){
            if (ch != null){
                ch.send(msg);
                return true;
            }
        }
        return false;
    }

    @Override
    public void send(String channel, T msg) {
        ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> topicsMap = DB.getTopicsMap();
        ConcurrentHashMap topicSubsMap = topicsMap.get(channel);
        //sync to prevent unregistering user from a topic's map while trying to send him a message
        synchronized (topicSubsMap){
            if (topicSubsMap != null) {
                for (Object sub : topicSubsMap.values()) {
                    ConcurrentHashMap<Integer, ConnectionHandler<T>> clientsMap = DB.getClientsMap();
                    clientsMap.get((Integer) sub).send(msg);
                }
            }
        }
    }

    @Override
    public void disconnect(int connectionId) {
        ConcurrentHashMap<Integer, ConnectionHandler<T>> clientsMap = DB.getClientsMap();
        if (clientsMap.containsKey(connectionId)){
            ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> topicsMap = DB.getTopicsMap();
           for (ConcurrentHashMap<Integer, Integer> topicSubsMap : topicsMap.values()){
               //sync to prevent unregistering user from a topic's map while trying to send him a message
               synchronized (topicSubsMap){
                   if (topicSubsMap != null){
                        topicSubsMap.remove(connectionId);
                   }
               }
           }
            ConcurrentHashMap<Integer, ConcurrentHashMap<String, Integer>> subscribersMap = DB.getSubscribersMap();
            ConcurrentHashMap<String, Integer> userSubsIDs = subscribersMap.get(connectionId);
           synchronized (userSubsIDs){
               if (userSubsIDs != null){
                   userSubsIDs.clear();
               }
               subscribersMap.remove(connectionId);
           }
            //sync to prevent unregistering user while trying to send him a message
           synchronized (clientsMap){
               clientsMap.remove(connectionId);
           }
           ConcurrentHashMap<Integer, User> UsersIntegerMap = DB.getUserIntegerMap();
           synchronized (UsersIntegerMap){
               User user = UsersIntegerMap.get(connectionId);
               synchronized (user){
                   user.setId(-1);
               }
               UsersIntegerMap.remove(connectionId);
           }
        }
    }
}
