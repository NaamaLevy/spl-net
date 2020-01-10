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
        synchronized (lock) {
            usersMap.get(connectionId).send(msg);
            return true;
        }
    }

    @Override
    public void send(String channel, T msg) {
        ConcurrentHashMap topicSubsMap = topicsMap.get(channel);
        if (topicSubsMap != null) {
            for (Object sub : topicSubsMap.values()) {
                usersMap.get((Integer) sub).send(msg);
            }
        }
    }

    @Override
    public void disconnect(int connectionId) {

    }
}
