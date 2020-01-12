package bgu.spl.net.srv;

import java.util.LinkedList;
import java.util.List;

public class User {
    //fields
    String userName;
    String password;
    int id;
    ConnectionHandler CH;
    List subscribedTo = new LinkedList<String>();

    //constructor
    public User(ConnectionHandler CH, int id){
        this.CH = CH;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public ConnectionHandler getCH() {
        return CH;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSubscribed(String topic) {return subscribedTo.contains(topic); }


}

