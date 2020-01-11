package bgu.spl.net.srv;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class ConnectionsImpl<T> implements Connections<T> {

    //fields
    private ConcurrentHashMap<Integer, ConcurrentHashMap<String, Integer>> subscribersMap;
    private ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> topicsMap;
    private ConcurrentHashMap<Integer, ConnectionHandler<T>> usersMap;
    private final Object lock;

    public ConnectionsImpl() {
        subscribersMap = new ConcurrentHashMap<>();
        topicsMap = new ConcurrentHashMap<>();
        usersMap = new ConcurrentHashMap<>();
        lock = new Object();
    }

    @Override
    public boolean send(int connectionId, T msg) {
        if (!usersMap.containsKey(connectionId))
            return false;
        ConnectionHandler ch = usersMap.get(connectionId);
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
                    usersMap.get((Integer) sub).send(msg);
                }
            }
        }

    }

    @Override
    public void disconnect(int connectionId) {
        if (usersMap.containsKey(connectionId)){
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
           synchronized (usersMap){
               usersMap.remove(connectionId);
           }
        }
    }
}
