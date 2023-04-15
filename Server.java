import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    private ArrayList<ConnectionHandler> connections;
    
    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(6666);
            Socket client = server.accept();
            ConnectionHandler handler = new ConnectionHandler(client);
            ConnectionHandler.addNewConnection(handler);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}