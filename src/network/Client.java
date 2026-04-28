package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 5555;

    public Response sendRequest(Request request) {
        try (
                Socket socket = new Socket(HOST, PORT);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            out.writeObject(request);
            out.flush();

            return (Response) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, "Connection error", null);
        }
    }
}