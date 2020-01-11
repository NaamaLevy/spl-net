package bgu.spl.net.impl.stomp;

import java.util.HashMap;

public class ERRORframe extends Frame{

    public ERRORframe(String command, HashMap<String , String> header, String body) {
        super(command,  header, body);
    }

    public void process(){

    }


}
