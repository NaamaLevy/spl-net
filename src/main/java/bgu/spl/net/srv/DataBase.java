package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBase<T> {
    //fields
    private ConcurrentHashMap<Integer, ConcurrentHashMap<String, Integer>> subscribersMap;
    private ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> topicsMap;  // holds inner map like this: <connectionId, topicId>
    private ConcurrentHashMap<Integer, ConnectionHandler<T>> clientsMap;
    private ConcurrentHashMap<String, User> userStringMap;
    private ConcurrentHashMap<Integer, User> userIntegerMap;
    AtomicInteger nextid;

    private static class SingletonHolder {
        private static DataBase dataBase = new DataBase();
    }

    private DataBase(){
        nextid = new AtomicInteger(0);
        subscribersMap = new ConcurrentHashMap<>();
        topicsMap = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>();
        clientsMap = new ConcurrentHashMap<>();
        userStringMap = new ConcurrentHashMap<>();
        userIntegerMap = new ConcurrentHashMap<>();
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

    public ConcurrentHashMap<String, User> getUserStringMap() {
        return userStringMap;
    }
    public ConcurrentHashMap<Integer, User> getUserIntegerMap() { return userIntegerMap; }


    public synchronized int addUser(ConnectionHandler CH){
        int id = nextid.get();
        clientsMap.put(id,CH);
        nextid.incrementAndGet();
        return id;
    }
    public int isUserExist(String userName){
            if (userStringMap.containsKey(userName)){
                return  userStringMap.get(userName).getId();
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
        return userStringMap.get(name);
    }

    public User getUserByConnectionId(int connectionId){
        return userIntegerMap.get(connectionId);
           }

    public void addSubscriberToTopic(int connectionId, String topic, int topicId){
        User user = getUserByConnectionId(connectionId);
        if(!user.isSubscribed(topic)) { // if user is not already subscribed to this topic
            if (subscribersMap.get(connectionId) != null) { //if the user is already exist in subscribersMap
                subscribersMap.get(connectionId).put(topic, topicId);
            } else { //in case user doesn't have a subscribed topic map
                ConcurrentHashMap newSubscribedMap = new ConcurrentHashMap<String, Integer>();
                subscribersMap.put(connectionId, newSubscribedMap);
                subscribersMap.get(connectionId).put(topic, topicId);
            }
        }
        else{
            //user is already subscribed to topic. should send an error? QQQ
        }
    }

    public void addTopicAsSubscribed(int connectionId, String topic, int topicId){
        User user = getUserByConnectionId(connectionId);
        if(!user.isSubscribed(topic)) { // if user is not already subscribed to this topic
            if (topicsMap.get(topic) != null) { // if there is already subscribers to this topic
                topicsMap.get(topic).put(connectionId, topicId);
                user.subscribedTo.add(topic);
            }
            else{
                ConcurrentHashMap newTopicdMap = new ConcurrentHashMap<Integer, Integer>();
                topicsMap.put(topic, newTopicdMap);
            }
        }
        else{
            //user is already subscribed to topic. should send an error? QQQ
        }
    }
}

