package Client;


import java.io.*;
import java.net.*;

    public class ClientQuizClash implements MessageSender {
        private static final String SERVER_IP = "127.0.0.1";
        private static final int PORT = 9999;
        private PrintWriter output;

        public ClientQuizClash (Socket socket) throws IOException {
            this.output = new PrintWriter(socket.getOutputStream(), true);
        }

        @Override
        public void sendMessage(String message) {
            if (message != null) {
                output.println(message);
            }
        }

        public static void main(String[] args) {
            try {
                Socket socket = new Socket(SERVER_IP, PORT);
                System.out.println("Connected to server.");

                ClientQuizClash client = new ClientQuizClash(socket);

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));



                // Start a thread to handle incoming messages from the server
                Thread receiveThread = new Thread(() -> {
                    try {
                        String message;
                        while ((message = input.readLine()) != null) {
                            System.out.println("Server: " + message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                receiveThread.start();
                Quizkampen Qk = new Quizkampen(client);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
