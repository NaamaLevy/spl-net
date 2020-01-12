package bgu.spl.net.srv;

public class User {
    //fields
    String userName;
    String password;
    int id;

    //constructor
    public User(String userName, String password, int id){
        this.userName = userName;
        this.password = password;
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
}
