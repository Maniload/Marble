package de.craplezz.marble;

import de.craplezz.marble.connection.HandshakeConnection;
import de.craplezz.marble.status.ServerStatus;
import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Overload
 * @version 1.0
 */
public class Marble {

    @Getter
    private final List<HandshakeConnection> players = new ArrayList<>();

    @Getter
    private int maxPlayers = 100;

    @Getter
    private String messageOfTheDay = "Marble is live!";

    public Marble() {
        try {
            System.out.println("Starting Marble 1.0, supporting Minecraft 1.12.x.");

            ServerSocket server = new ServerSocket(25565);
            ExecutorService executor = Executors.newCachedThreadPool();

            System.out.println("Listening on port " + server.getLocalPort() + ".");

            while (!server.isClosed()) {
                try {
                    Socket socket = server.accept();

                    HandshakeConnection connection = new HandshakeConnection(this, socket);

                    executor.execute(connection::accept);

                    players.add(connection);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerStatus getServerStatus() {
        return new ServerStatus(
                new ServerStatus.Version("Marble 1.11.x", 316),
                new ServerStatus.Players(100, players.size()),
                new ServerStatus.Description(messageOfTheDay)
        );
    }

    public static void main(String[] args) {
        new Marble();
    }

}
