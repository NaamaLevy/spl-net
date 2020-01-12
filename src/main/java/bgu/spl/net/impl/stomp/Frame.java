package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.DataBase;

import java.util.HashMap;

public class Frame {

        private final String command;
        private final HashMap<String , String> headers;
        private final String body;


        public Frame(String command, HashMap<String , String> header, String body, DataBase DB) {
            this.command = command;
            this.headers = header;
            this.body = body;
        }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void process(){}
    }


