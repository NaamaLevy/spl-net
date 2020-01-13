package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.DataBase;

import java.util.HashMap;

public class UNSUBSCRIBEframe extends Frame{

    //fields
    int topicId

    //constructor
    public UNSUBSCRIBEframe(String command, HashMap<String , String> headers, String body, DataBase DB, int connectionId) {
        super(command,  headers, body, DB, connectionId);
    }

    public void process(){
        topicId = Integer.parseInt(headers.get("id"));

    }

}
