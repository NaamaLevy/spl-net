package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.DataBase;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SUBSCRIBEframe extends Frame{

    String topicToSubscribe;
    int connectionId;

    public SUBSCRIBEframe(String command, HashMap<String , String> headers, String body, DataBase DB, int connectionId) {
        super(command,  headers, body, DB, connectionId);
    }

    public void process(){
        topicToSubscribe = headers.get("destination");
        DB.addSubscriberToTopic(connectionId, topicToSubscribe);
        DB.addTopicAsSubscribed(connectionId, topicToSubscribe);
        //send receipt


    }

}

