public class MainClient {

    public static void main(String[] args) {

        Client client = new Client();

        client.askConnection();


        while (true) {
            client.write();
            client.read();
        }


    }


}
