# About NetChat

The Java TCP client-server group chat app, <b>NetChat</b>, with encrypted messages is a simple yet effective solution for secure communication within a group. This project enables multiple clients to connect to a single server, allowing them to exchange messages with one another in real-time. The app incorporates encryption algorithms to ensure the confidentiality of messages being sent between the clients.

The application is built using Java Socket programming to establish a TCP connection between the server and clients. The chat messages are encrypted using symmetric encryption techniques such as AES or DES before being sent over the network. The server acts as a mediator between the clients and ensures that each message is delivered to the intended recipients.

Overall, the Java TCP client-server group chat app with encrypted messages is an excellent choice for organizations or groups that require secure communication channels. It provides a convenient and reliable way for users to communicate and collaborate, ensuring that their messages remain private and secure.

# How to run NetChat

### Requirements
- You need to have Java installed on your system

### Server Options
- To run <b>NetChat</b> on your local machine change the client socket host in `Client.java` to use the host `127.0.0.1`. This is set by default.
- To run <b>NetChat</b> on your local network:
    1. Identifyy the IP address of the computer where the <b>NetChat</b> server will run. You can do this by opening a command prompt or terminal and typing `ipconfig` or `ifconfig`.
    2. Make sure that the computer's firewall is configured to allow incoming connections on the port used by the <b>NetChat</b> server.
    3. Change the host to the IP address.

### Step 1: Compile
First compile `Server.java` and `Client.java`.
- `javac Server.java`
- `javac Client.java`

### Step 2: Start the server
Start the server on one machine.
- `java Server`

### Step 3: Start the client
The client should be started on all machines that you intend to join the chat on.
- `java Client`
