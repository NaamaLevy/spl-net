package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.DataBase;

import java.util.HashMap;

public class SENDframe extends Frame{

    public SENDframe(String command, HashMap<String , String> headers, String body, DataBase DB) {
        super(command,  headers, body, DB);;;
    }

    public void process(){

    }


}
