package bgu.spl.net.srv;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.impl.stomp.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;

public class StompMessagingProtocolImpl<T> implements StompMessagingProtocol<T> {
    //fields
    boolean shouldTerminate;
    int userCounter;
    Connections<String> connections;
    BufferedReader bufferedReader;
    DataBase DB;
    int connectionId;

    //constructor
    public StompMessagingProtocolImpl() {
        shouldTerminate = false;
        userCounter = 0;
        DB = DataBase.getInstance();
    }


    public void start(int connectionId, Connections<String> connections) {
        this.connectionId = connectionId;
    }


    public void process(String message) throws IOException {
//        String[] messageVector = message.split("/n");
//        String command = messageVector[0];

        Reader reader = new StringReader(message);
        BufferedReader bufferedMessage = new BufferedReader(reader);
        if (bufferedMessage.ready()) {
            try {
                //gets command
                String command = bufferedMessage.readLine();
                // gets headers
                HashMap<String, String> headers = new HashMap();
                String header;
                System.out.println(command);
                while (((header = bufferedMessage.readLine()).length()) > 0) {
                    int index = header.indexOf(':');
                    System.out.println(index);
                    String key = header.substring(0, index);
                    String value = header.substring(index + 1, header.length());
                    headers.put(key.trim(), value.trim());
                }
                // gets body
                StringBuffer bodyAsBuffer = new StringBuffer();
                char c;
                while ((c = (char) bufferedMessage.read()) != '\u0000') {
                    bodyAsBuffer.append(c);
                }
                String bodyAsString = bodyAsBuffer.toString();
                if (command.equals("CONNECT")) {
                    Frame newFrame = new CONNECTframe(command, headers, bodyAsString, DB, connectionId);
                    newFrame.process();
                }
//                else if (command.equals("MESSAGE")) {
//                    Frame newFrame = new MESSAGEframe(command, headers, bodyAsString, DB, connectionId);
//                    newFrame.process();
//                } else if (command.equals("RECEIPT")) {
//                    Frame newFrame = new RECEIPTframe(command, headers, bodyAsString, DB, connectionId);
//                    newFrame.process();
//                } else if (command.equals("ERROR")) {
//                    Frame newFrame = new ERRORframe(command, headers, bodyAsString, DB, connectionId);
//                    newFrame.process();
//                }
                else if (command.equals("SEND")) {
                    Frame newFrame = new SENDframe(command, headers, bodyAsString, DB, connectionId);
                    newFrame.process();
                } else if (command.equals("SUBSCRIBE")) {
                    Frame newFrame = new SUBSCRIBEframe(command, headers, bodyAsString, DB, connectionId);
                    newFrame.process();
                } else if (command.equals("UNSUBSCRIBE")) {
                    Frame newFrame = new UNSUBSCRIBEframe(command, headers, bodyAsString, DB, connectionId);
                    newFrame.process();
                } else if (command.equals("DISCONNECT")) {
                    Frame newFrame = new DISCONNECTframe(command, headers, bodyAsString, DB, connectionId);
                    newFrame.process();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean shouldTerminate() { return shouldTerminate; }
}
