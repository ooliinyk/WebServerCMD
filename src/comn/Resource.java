package comn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by user on 26.09.2016.
 */
public class Resource {

    public static void main(String[] args) throws IOException {

        WebServer webServer = new WebServer();

        Socket socket = new Socket("localhost", Integer.valueOf(new HttpDConfig("httpd.conf").getConfigurationMap().get("Listen").get(0)));
        sendMessage(socket);
        readResponse(socket);
        socket.close();

    }

    public static void sendMessage(Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        HttpRequest request = new HttpRequest();

        String message = request.getHeaders() + "\njpg => " + request.mimeTypeLookUp("jpg")
                + "\nmp4 => " + request.mimeTypeLookUp("mp4");
        out.println(message + "END");
        System.out.println("-------------------------");
        System.out.println("I just sent:\n" + message);
    }

    public static void readResponse(Socket socket) throws IOException {
        BufferedReader response = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
        String line;
        while ((line = response.readLine()) != null) {
            System.out.println("+ " + line);
        }
        System.out.println("-------------------------");
    }
}
