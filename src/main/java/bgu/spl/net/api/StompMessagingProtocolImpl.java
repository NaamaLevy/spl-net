package bgu.spl.net.api;

import bgu.spl.net.impl.rci.Command;
import bgu.spl.net.impl.stomp.CONNECTframe;
import bgu.spl.net.srv.Connections;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Scanner;

public class StompMessagingProtocolImpl<T> implements StompMessagingProtocol {

    boolean shouldTerminate = false;
    int userCounter;
    Connections<String> connections;
    BufferedReader bufferedReader;

    public StompMessagingProtocolImpl(){
        shouldTerminate = false;
        userCounter = 0;
    }



    public void start(int connectionId, Connections<String> connections){




        //login
        // send "CONNECTED"


    };


    public void process(String message){
        try {
                if (_input.ready()) {

                    if (command.length() > 0) {
                        try {
                            Command c = Command.valueOf( command );
                            // Get headers
                            HashMap headers = new HashMap();
                            String header;
                            while ((header = _input.readLine()).length() > 0) {
                                int ind = header.indexOf( ':' );
                                String k = header.substring( 0, ind );
                                String v = header.substring( ind+1, header.length() );
                                headers.put(k.trim(),v.trim());
                            }
                            // Read body
                            StringBuffer body = new StringBuffer();
                            int b;
                            while ((b = _input.read()) != 0) {
                                body.append( (char)b );
                            }

                            try {
                                _receiver.receive( c, headers, body.toString() );
                            } catch (Exception e) {
                                // We ignore these errors; we don't want client code
                                // crashing our listener.
                            }
                        } catch (Error e) {
                            try {
                                while (_input.read() != 0);
                            } catch (Exception ex) { }
                            try {
                                _receiver.receive( Command.ERROR, null, e.getMessage()+"\n" );
                            } catch (Exception ex) {
                                // We ignore these errors; we don't want client code
                                // crashing our listener.
                            }
                        }
                    }
                } else {
                    if (_receiver.isClosed()) {
                        _receiver.disconnect();
                        return;
                    }
                    try {Thread.sleep(200);}catch(InterruptedException e){interrupt();}
                }
            }
        } catch (IOException e) {
            // What do we do with IO Exceptions?  Report it to the receiver, and
            // exit the thread.
            System.err.println("Stomp exiting because of exception");
            e.printStackTrace( System.err );
            _receiver.receive( Command.ERROR, null, e.getMessage() );
        } catch (Exception e) {
            System.err.println("Stomp exiting because of exception");
            e.printStackTrace( System.err );
            _receiver.receive( Command.ERROR, null, e.getMessage() );
        }
    }












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
                new CONNECTframe(String command, HashMap)
                        .process

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
