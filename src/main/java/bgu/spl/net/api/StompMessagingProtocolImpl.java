package bgu.spl.net.api;

import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.srv.Connections;

import java.util.HashMap;
import java.util.Scanner;

public class StompMessagingProtocolImpl<T> implements StompMessagingProtocol {

    boolean shouldTerminate = false;
    int userCounter;
    Connections<String> connections;

    public StompMessagingProtocolImpl(){
        shouldTerminate = false;
        userCounter = 0;
    }



    public void start(int connectionId, Connections<String> connections){




        //login
        // send "CONNECTED"


    };


    public void process(String message){
        Scanner scanner = new Scanner(message);
        while (scanner.hasNextLine()) {
            //takes the command
            String command = scanner.nextLine();
            HashMap headers = new HashMap();
            String header;
            //takes the different headers
            while ((header = scanner.nextLine()).length() > 0){
                int index = header.indexOf( ':' );
                String key = header.substring( 0, index );
                String value = header.substring( index+1, header.length() );
                headers.put(key.trim(),value.trim());
            }
            //takes the message's body
            StringBuffer body = new StringBuffer();
            int b;
            while ((b = _input.read()) != 0) {
                body.append( (char)b );
            }

            if(command.equals("CONNECT")){
                new CONNECTframe

             }
            else if(command.equals("MESSAGE")) {

             }
             else if(command.equals("RECEIPT")) {

             }
             else if(command.equals("ERROR")) {

             }
             else if(command.equals("SEND")) {

             }
             else if(command.equals("SUBSCRIBE")) {

             }
             else if(command.equals("UNSUBSCRIBE")) {

             }
             else if(command.equals("DISCONNECT")) {

             }

        }
        scanner.close();






    };







    public boolean shouldTerminate(){


        return shouldTerminate;

    };

}
