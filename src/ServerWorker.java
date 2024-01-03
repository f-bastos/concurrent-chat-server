import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerWorker implements Runnable {

    // - cada serverWorker vai ficar responsável por cada cliente e é runnable

    List<ServerWorker> serverWorkerList;
    Socket clientSocket;
    String messageReceived;


    // construtor
    public ServerWorker(Socket clientSocket, List<ServerWorker> serverWorkerList) {
        this.clientSocket = clientSocket;
        this.serverWorkerList = serverWorkerList;
    }



    public void receive() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            messageReceived = in.readLine();

            System.out.println("Received from " + serverWorkerList.indexOf(this) + ": " + messageReceived);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void send(String messageReceived) {

        try {

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println(messageReceived);
            out.flush();

            System.out.println("Sending: " + messageReceived);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    // sendAll()
    public void sendAll(String messageReceived) {

        try {
            for (ServerWorker s : serverWorkerList) {
                PrintWriter out = new PrintWriter(s.clientSocket.getOutputStream(), true);

                out.println("Client " + serverWorkerList.indexOf(this) + ": " + messageReceived);
                out.flush();

                System.out.println("Sending to Client " + serverWorkerList.indexOf(s) + ": " + messageReceived);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {

        System.out.println("Client " + serverWorkerList.indexOf(this) + " connected");
        System.out.println("Running inside thread: " + Thread.currentThread().getName());

        while (true) {
            receive();
            sendAll(messageReceived);
        }

    }
}
