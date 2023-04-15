import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private Socket client;
    private BufferedReader input;
    private PrintWriter output;

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 6666);
            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
}
