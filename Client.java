import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private boolean done;

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

    class InputHandler implements Runnable {
    
        @Override
        public void run() {
            
            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inReader.readLine();
                    if (message.equals("/quite")) {
                        inReader.close();
                        shutdown();
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    
        public void shutdown() {
            done = true;
            try {
                input.close();
                output.close();
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        
    }
    
}
