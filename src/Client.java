import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public String hostName = "localhost";
    int portNumber = 6666;
    Socket clientSocket;


    public void askConnection() {
        try {
            clientSocket = new Socket(hostName, portNumber);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void write() {

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String output = scanner.nextLine();
            out.println(output);
            out.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    // lê a mensagem que recebe. Deveria ter um print para a imprimir no terminal ??
    public void read() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String messageReceived = in.readLine();
            System.out.println(messageReceived);

            out.print(messageReceived);
            out.flush();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
