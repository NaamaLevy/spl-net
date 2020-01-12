package bgu.spl.net.impl.stomp;

import java.util.HashMap;

public class CONNECTframe extends Frame{

    public CONNECTframe(String command, HashMap<String , String> headers, String body) {
        super(command,  headers, body);
    }

    public void process(){
        String user = getHeaders().get("login");
        String password = getHeaders().get("passcode");
        String version = getHeaders().get("version");
        if (

        )
    }


}
