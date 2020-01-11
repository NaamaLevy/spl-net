package bgu.spl.net.impl.stomp;

import java.util.HashMap;

public class UNSUBSCRIBEframe extends Frame{

    public UNSUBSCRIBEframe(String command, HashMap<String , String> header, String body) {
        super(command,  header, body);
    }

    public void process(){

    }

}
