import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public final static int PORT = 6666;
    private ServerSocket serverSocket;
    private List<ServerWorker> serverWorkerList;
    private ExecutorService threadPool;



    // construtor server
    public Server() {
        this.serverWorkerList = new LinkedList<>();
        this.threadPool = Executors.newCachedThreadPool();
    }


    public void listen() {
        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Server connected in the adress " + serverSocket.getInetAddress() + ", port " + serverSocket.getLocalPort());
            System.out.println("Listening ...");

            acceptConection();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void acceptConection() {

        while (true) {

            try {
                // aceitar ligaçao e criar client socket
                Socket clientSocket = serverSocket.accept();

                // criar serverWorker para lidar com o cliente
                ServerWorker serverWorker = new ServerWorker(clientSocket, serverWorkerList);

                // adicionar serverWorker à lista de serverWorkers
                serverWorkerList.add(serverWorker);

                // "start" da thread
                threadPool.submit(serverWorker);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
