package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;

public abstract class BaseServer<T> implements Server<T> {

    private final int port;
    private final Supplier<StompMessagingProtocol<T>> protocolFactory;
    private final Supplier<MessageEncoderDecoder<T>> encdecFactory;
    private final ConnectionsImpl<String> connections;
    private ServerSocket sock;
    private DataBase DB;

    public BaseServer(
            int port,
            Supplier<StompMessagingProtocol<T>> protocolFactory,  //QQQ understand if the supplier can provide our specific protocol (StompMessagingProtocol)
            Supplier<MessageEncoderDecoder<T>> encdecFactory) {
        this.connections = ConnectionsImpl.getInstance();
        this.port = port;
        this.protocolFactory = protocolFactory;
        this.encdecFactory = encdecFactory;
		this.sock = null;
		DB = DataBase.getInstance();
    }

    @Override
    public void serve() {

        try (ServerSocket serverSock = new ServerSocket(port)) {
			System.out.println("Server started");

            this.sock = serverSock; //just to be able to close

            while (!Thread.currentThread().isInterrupted()) {

                Socket clientSock = serverSock.accept();
                StompMessagingProtocol protocol = protocolFactory.get();
                BlockingConnectionHandler<T> handler = new BlockingConnectionHandler(
                        clientSock,
                        encdecFactory.get(),
                       protocol, connections);
             //   int connectionId = handler.getConnectionID();
             //   protocol.start( connectionId, connections);
                execute(handler);
            }
        } catch (IOException ex) {
        }

        System.out.println("server closed!!!");
    }

    @Override
    public void close() throws IOException {
		if (sock != null)
			sock.close();
    }

    protected abstract void execute(BlockingConnectionHandler<T>  handler);

}
