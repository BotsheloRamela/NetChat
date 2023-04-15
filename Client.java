import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("127.0.0.1", 6666);
            ClientConnection clientConnection = new ClientConnection(clientSocket);
            Thread thread = new Thread(clientConnection);
            thread.start();

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                if (userInput.equals("/quit")) {
                    clientConnection.disconnect();
                    consoleReader.close();
                    break;
                } else {
                    clientConnection.sendMessage(userInput);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
