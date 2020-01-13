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

    public SUBSCRIBEframe(String command, HashMap<String , String> headers, String body, DataBase DB, int connectionId) {
        super(command,  headers, body, DB, connectionId);
    }

    public void process(){
        topicToSubscribe = headers.get("destination");
        topicId = Integer.parseInt(headers.get("id"));
        DB.addSubscriberToTopic(connectionId, topicToSubscribe, topicId);
        DB.addTopicAsSubscriber(connectionId, topicToSubscribe, topicId);
        Connections connections = ConnectionsImpl.getInstance();
        connections.send(connectionId, buildReceipt(topicId)); // sends RECEIPT to the user
    }

}

