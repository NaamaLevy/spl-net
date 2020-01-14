package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.DataBase;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SENDframe extends Frame{
    AtomicInteger messageid;

    public SENDframe(String command, HashMap<String , String> headers, String body, DataBase DB, int connectionId) {
        super(command,  headers, body, DB, connectionId);
        messageid = new AtomicInteger(0);
    }

    public void process(){
        ConnectionsImpl connections = ConnectionsImpl.getInstance();
        String destination = getHeaders().get("destination");
        ConcurrentHashMap<String, Integer> userSubs = (ConcurrentHashMap<String, Integer>) DB.getSubscribersMap().get(connectionId);
        int subid = userSubs.get(destination);
        connections.send(destination, buildMESSAGE(destination, subid, messageid, body)); // sends RECEIPT to the user
    }

    private String buildMESSAGE(String destination, int subID, AtomicInteger messageid, String body){
        // CONNECTED frame to the client and the client will print "Login successful”.
        String command = "MESSAGE";
        char newLine = '\n';
        char close = '\u0000';
        return command + newLine + "subscription: " + subID + newLine + "Message-id: " + messageid + newLine + "destination: " + destination + newLine + body +newLine+newLine+newLine+close;
    }
}
