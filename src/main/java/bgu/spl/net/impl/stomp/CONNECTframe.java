package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.DataBase;
import bgu.spl.net.srv.User;

import java.util.HashMap;

public class CONNECTframe extends Frame{

    public CONNECTframe(String command, HashMap<String , String> headers, String body, DataBase DB, int connectionId) {
        super(command,  headers, body, DB, connectionId);
        ConnectionsImpl connections = ConnectionsImpl.getInstance();
    }

    public void process(){
        String userName = getHeaders().get("login");
        String password = getHeaders().get("passcode");
        String version = getHeaders().get("version");

        int exist = DB.isUserExist(userName);
        if (exist == -2){//new user
            User newUser = User(DB.getClientsMap().get(), connectionId);
            newUser.setPassword(password);
            newUser.setUserName(userName);
            DB.getUserStringMap().put(userName, newUser);
            DB.getUserIntegerMap().put(connectionId,newUser); //TODO naama, this is the new map i've told you about. needs to be removed when disconnect
            // CONNECTED frame to the client and the client will print "Login successful”.
        }

        if (exist == -1){//logged out
            if (((User) DB.getUserStringMap().get(userName)).getPassword() == password){
                ((User) DB.getUserStringMap().get(userName)).setId(connectionId);
                //CONNECTED frame and the client will print to the screen "Login successful.” // TODO naama, have a look at the buildRECEIPT method i've made in Frame class. you can make something similar.
                                                                                                //(TODO) though I'm not sure Frame class is the right place for that. look at SUBSCRIBEFrame how i used it.

            }
            else {//incorrect password
                //ERROR frame indicating the reason -the output in this case should be “Wrong password”
            }
        }
        else {//user is logged in
            //STOMP error frame indicating the reason –the output in this case should be “User already logged in”
            }
    }
}
