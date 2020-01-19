package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.STOMPMessageEncoderDecoder;
import bgu.spl.net.srv.Server;
import bgu.spl.net.srv.StompMessagingProtocolImpl;

public class StompServer {

    public static void main(String[] args) {

//        if(args[1] =="tpc"){
            Server.threadPerClient(7777,
                    ()-> new StompMessagingProtocolImpl(),
                    STOMPMessageEncoderDecoder::new).serve();
//        }
//        if(args[1] == "reactor"){
//            Server.reactor(
//                    Runtime.getRuntime().availableProcessors(),
//                    Integer.parseInt(args[0]), //port
//                    () ->  new StompMessagingProtocolImpl(),
//                    STOMPMessageEncoderDecoder::new).serve();
//
//        }

    }


}
