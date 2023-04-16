import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    /**
     * The main method creates a new Socket object and a new ClientConnection object with the Socket object.
     * It then creates a new thread to run the ClientConnection object and starts the thread.
     * It reads user input from the console and sends it to the server via the ClientConnection object.
     * If the user enters "/quit", the method sends the message to the server, disconnects from the server, and exits the program.
     * Otherwise, the method continues to send user input to the server until the user enters "/quit".
     */
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("127.0.0.1", 6666); // create a new Socket object with the IP address and port of the server
            ClientConnection clientConnection = new ClientConnection(clientSocket); // create a new ClientConnection object with the Socket object
            Thread thread = new Thread(clientConnection); // create a new thread to run the ClientConnection object
            thread.start(); // start the thread

            // create a new BufferedReader object to read user input from the console
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in)); 
            String userInput;
            while ((userInput = consoleReader.readLine()) != null) { // read user input from the console and check if it's null
                if (userInput.equals("/quit")) {
                    clientConnection.sendMessage(userInput); // send the message to the server
                    clientConnection.disconnect(); // disconnect from the server
                    consoleReader.close(); // close the BufferedReader object
                    thread.interrupt(); // interrupt the thread to stop it
                    break; // exit the loop
                } else { // if the user didn't enter "/quit"
                    clientConnection.sendMessage(userInput); // send the message to the server
                }
            }
        } catch (Exception e) { // catch any exceptions that occur
            e.printStackTrace();
        }
    }
}
