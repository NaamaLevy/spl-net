package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.DataBase;
import bgu.spl.net.srv.User;

import java.util.HashMap;

public class CONNECTframe extends Frame{

    public CONNECTframe(String command, HashMap<String , String> headers, String body, DataBase DB, int id) {
        super(command,  headers, body, DB);
        ConnectionsImpl connections = ConnectionsImpl.getInstance();
    }

    public void process(){
        String userName = getHeaders().get("login");
        String password = getHeaders().get("passcode");
        String version = getHeaders().get("version");

        int exist = DB.isUserExist(userName);
        if (exist == -2){//new user
            User newUser = User(DB.getClientsMap().get(id), id);
            newUser.setPassword(password);
            newUser.setUserName(userName);
            DB.getUserMap().put(userName, newUser);
            // CONNECTED frame to the client and the client will print "Login successful”.
        }

        if (exist == -1){//logged out
            if (((User) DB.getUserMap().get(userName)).getPassword() == password){
                ((User) DB.getUserMap().get(userName)).setId(id);
                //CONNECTED frame and the client will print to the screen "Login successful.”
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
