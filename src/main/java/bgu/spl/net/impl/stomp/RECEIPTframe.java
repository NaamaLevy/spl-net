package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.DataBase;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RECEIPTframe extends Frame{

    public RECEIPTframe(String command, HashMap<String , String> headers, String body, DataBase DB, int connectionId) {
        super(command,  headers, body, DB, connectionId);
    }

    public void process(){
        ConnectionsImpl connections = ConnectionsImpl.getInstance();
        connections.send(connectionId, super.buildRECEIPT(connectionId)); // sends RECEIPT to the user
    }
}
