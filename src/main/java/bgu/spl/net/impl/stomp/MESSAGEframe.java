package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.DataBase;

import java.util.HashMap;

public class MESSAGEframe extends Frame{

    public MESSAGEframe(String command, HashMap<String , String> header, String body, DataBase DB) {
        super(command,  header, body, DB);;
    }

    public void process(){

    }


}
