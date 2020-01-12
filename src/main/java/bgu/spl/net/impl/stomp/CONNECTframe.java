package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.DataBase;
import bgu.spl.net.srv.User;

import java.util.HashMap;

public class CONNECTframe extends Frame{

    public CONNECTframe(String command, HashMap<String , String> headers, String body, DataBase DB) {
        super(command,  headers, body, DB);
        ConnectionsImpl connections = ConnectionsImpl.
    }

    public void process(){
        String userName = getHeaders().get("login");
        String password = getHeaders().get("passcode");
        String version = getHeaders().get("version");

        int exist = DB.isUserExist(userName);
        if (exist != -1){
            if (DB.isUserLoggedIn(exist)){
                //ERROR frame “User already logged in”
            }
            else if (DB.getUserMap().get(exist))
        }
    }

}
