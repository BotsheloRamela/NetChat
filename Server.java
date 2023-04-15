import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(6666);
            Socket client = server.accept();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
}