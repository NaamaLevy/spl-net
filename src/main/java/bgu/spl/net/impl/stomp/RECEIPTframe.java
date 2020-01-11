package bgu.spl.net.impl.stomp;

import java.util.HashMap;

public class RECEIPTframe extends Frame{

    public RECEIPTframe(String command, HashMap<String , String> header, String body) {
        super(command,  header, body);
    }

    public void process(){

    }


}
