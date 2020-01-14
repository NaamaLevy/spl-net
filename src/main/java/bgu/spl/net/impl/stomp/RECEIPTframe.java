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
        connections.send(connectionId, buildRECEIPT()); // sends RECEIPT to the user
    }
    private String buildRECEIPT(){
        // CONNECTED frame to the client and the client will print "Login successful”.
        String command = "RECEIPT";
        char newLine = '\n';
        char close = '\u0000';
        return command + newLine + "receipt-id: " + connectionId +newLine+newLine+newLine+close;
    }
}
