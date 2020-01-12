package bgu.spl.net.srv;

public class User {
    //fields
    String userName;
    String password;
    int id;
    ConnectionHandler CH;

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
}
