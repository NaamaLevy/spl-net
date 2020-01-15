package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.DataBase;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SUBSCRIBEframe extends Frame{

    String topicToSubscribe;
    int connectionId;
    int topicId;
    int receiptId;

    public SUBSCRIBEframe(String command, HashMap<String , String> headers, String body, DataBase DB, int connectionId) {
        super(command,  headers, body, DB, connectionId);
    }

    public void process(){
        Connections connections = ConnectionsImpl.getInstance();
        topicToSubscribe = headers.get("destination");
        topicId = Integer.parseInt(headers.get("receipt-id"));
        receiptId = Integer.parseInt(headers.get("receipt"));
        //subscribe user to topic
        DB.subscribeUser(connectionId, topicToSubscribe,topicId);
        //return to user RECEIPT for this frame
        connections.send(connectionId, buildRECEIPT(receiptId)); // sends RECEIPT to the user
    }
}

