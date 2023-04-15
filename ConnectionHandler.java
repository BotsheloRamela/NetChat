import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable{

    private Socket client;
    private BufferedReader input;
    private PrintWriter output;

    public ConnectionHandler(Socket client) {
        this.client = client;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
