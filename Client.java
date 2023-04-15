import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 6666);
            BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter socketOutput = new PrintWriter(socket.getOutputStream(), true);
            InputHandler inputHandler = new InputHandler(socketOutput);
            Thread inputThread = new Thread(inputHandler);
            inputThread.start();
            String message;
            while ((message = socketInput.readLine()) != null) {
                System.out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
