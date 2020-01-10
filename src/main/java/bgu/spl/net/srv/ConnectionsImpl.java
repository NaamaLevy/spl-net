package bgu.spl.net.srv;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsImpl<T> implements Connections<T>{

    //fields
    private ConcurrentHashMap<Integer, BlockingQueue> usersMap;
    private ConcurrentHashMap<String, BlockingQueue> topicsMap;

    @Override
    public boolean send(int connectionId, T msg) {
        return false;
    }

    @Override
    public void send(String channel, T msg) {

    }

    @Override
    public void disconnect(int connectionId) {

    }
}
