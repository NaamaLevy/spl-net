package bgu.spl.net.srv;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class ConnectionsImpl<T> implements Connections<T> {

    //fields
    private DataBase DB;
    private final Object lock;

    public ConnectionsImpl() {
        DB = DataBase.getInstance();
        lock = new Object();
    }

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
        ConcurrentHashMap topicSubsMap = topicsMap.get(channel);
        //sync to prevent unregistering user from a topic's map while trying to send him a message
        synchronized (topicSubsMap){
            if (topicSubsMap != null) {
                for (Object sub : topicSubsMap.values()) {
                    clientsMap.get((Integer) sub).send(msg);
                }
            }
        }

    }

    @Override
    public void disconnect(int connectionId) {
        if (clientsMap.containsKey(connectionId)){
           for (ConcurrentHashMap<Integer, Integer> topicSubsMap : topicsMap.values()){
               //sync to prevent unregistering user from a topic's map while trying to send him a message
               synchronized (topicSubsMap){
                   if (topicSubsMap != null){
                        topicSubsMap.remove(connectionId);
                   }
               }
           }
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
        }
    }
    public synchronized void addUser(String userName, String password){
        User newUser = new User(userName, password, nextid);

        nextid++;
    }

}
