package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBase<T> {
    //fields
    private ConcurrentHashMap<Integer, ConcurrentHashMap<String, Integer>> subscribersMap;
    private ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> topicsMap;
    private ConcurrentHashMap<Integer, ConnectionHandler<T>> clientsMap;
    private ConcurrentHashMap<String, User> userMap;
    AtomicInteger nextid;

    private static class SingletonHolder {
        private static DataBase dataBase = new DataBase();
    }

    private DataBase(){
        nextid = new AtomicInteger(0);
        subscribersMap = new ConcurrentHashMap<>();
        topicsMap = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>();
        clientsMap = new ConcurrentHashMap<>();
        userMap = new ConcurrentHashMap<>();
    }

    public static DataBase getInstance(){ return SingletonHolder.dataBase;}

    public ConcurrentHashMap<Integer, ConcurrentHashMap<String, Integer>> getSubscribersMap() {
        return subscribersMap;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> getTopicsMap() {
        return topicsMap;
    }

    public ConcurrentHashMap<Integer, ConnectionHandler<T>> getClientsMap() {
        return clientsMap;
    }

    public ConcurrentHashMap<String, User> getUserMap() {
        return userMap;
    }

    public synchronized int addUser(ConnectionHandler CH){
        int id = nextid.get();
        clientsMap.put(id,CH);
        nextid.incrementAndGet();
        return id;
    }
    public int isUserExist(String userName){
            if (userMap.containsKey(userName)){
                return  userMap.get(userName).getId();
             }
        return -2;
    }

    public boolean isUserLoggedIn(int id){
        if (clientsMap.containsKey(id)){
                return true;
        }
        return false;
    }

    public User getUserByName(String name){
        return userMap.get(name);
    }

    public User getUserByConnectionId(int connectionId){

       //TODO
    }

    public void addSubscriberToTopic(int connectionId, String topic){
        User user = getUserByConnectionId(connectionId);
        if(!user.isSubscribed()){
            //TODO add him to the relevent topic's map
        }
    }

    public void addTopicAsSubscribed(int connectionId, String topic){
        //TODO should we hold Map <connectionId, User> as well?
        //TODO add the topic to the user's map at subscribers Map
    }



}

