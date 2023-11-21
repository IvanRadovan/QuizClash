package Client;


import java.io.*;
import java.net.*;

    public class ClientQuizClash {
        private static final String SERVER_IP = "127.0.0.1";
        private static final int PORT = 9999;



        public static void main(String[] args) {
            try {
                Socket socket = new Socket(SERVER_IP, PORT);
                System.out.println("Connected to server.");

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

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
                Quizkampen Qk = new Quizkampen();

                // Continuously send messages to the server
                String userInputMessage;
                while ((userInputMessage = userInput.readLine()) != null) {
                    output.println(userInputMessage);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
