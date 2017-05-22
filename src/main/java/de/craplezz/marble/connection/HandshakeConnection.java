package de.craplezz.marble.connection;

import com.google.gson.Gson;
import de.craplezz.marble.Marble;
import de.craplezz.marble.connection.packet.PacketInputStream;
import de.craplezz.marble.connection.packet.PacketOutputStream;
import de.craplezz.marble.connection.state.ClientConnectionState;
import de.craplezz.marble.connection.state.ServerConnectionState;
import de.craplezz.marble.packet.Packet;
import de.craplezz.marble.packet.handshake.serverbound.HandshakePacket;
import de.craplezz.marble.packet.login.clientbound.DisconnectPacket;
import de.craplezz.marble.packet.login.clientbound.LoginSuccessPacket;
import de.craplezz.marble.packet.login.serverbound.LoginStartPacket;
import de.craplezz.marble.packet.play.clientbound.*;
import de.craplezz.marble.packet.play.serverbound.ChatMessageRequestPacket;
import de.craplezz.marble.packet.play.serverbound.ClientSettingsPacket;
import de.craplezz.marble.packet.play.serverbound.KeepAliveResponsePacket;
import de.craplezz.marble.packet.status.clientbound.PongPacket;
import de.craplezz.marble.packet.status.clientbound.ResponsePacket;
import de.craplezz.marble.packet.status.serverbound.PingPacket;
import de.craplezz.marble.packet.status.serverbound.RequestPacket;
import de.craplezz.marble.util.BlockPosition;
import de.craplezz.marble.util.ChatMessage;
import lombok.Getter;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Overload
 * @version 1.0
 */
public class HandshakeConnection {

    private static final Gson GSON = new Gson();

    private final Marble marble;
    private final Socket socket;

    private final PacketOutputStream outputStream;
    private final PacketInputStream inputStream;

    @Getter
    private String userName;

    public HandshakeConnection(Marble marble, Socket socket) throws IOException {
        this.marble = marble;
        this.socket = socket;

        // We start of by accepting handshake packets, our response is unknown at the moment
        this.inputStream = new PacketInputStream(new BufferInputStream(socket.getInputStream()), ClientConnectionState.HANDSHAKE);
        this.outputStream = new PacketOutputStream(new BufferOutputStream(socket.getOutputStream()), null);
    }

    public synchronized void accept() {
        try {
            // Accept handshake packet
            Packet packet = inputStream.read();

            if (!(packet instanceof HandshakePacket)) {
                throw new IOException("Expected handshake packet but got " + packet.getType());
            }

            switch (((HandshakePacket) packet).getNextStatus()) {
                case 1:
                    // Switch to status state
                    inputStream.setConnectionState(ClientConnectionState.STATUS);

                    // Accept request
                    packet = inputStream.read();

                    if (!(packet instanceof RequestPacket)) {
                        throw new IOException("Expected request packet but got " + packet.getType());
                    }

                    // Send response packet
                    outputStream.write(new ResponsePacket(GSON.toJson(marble.getServerStatus())));

                    // Read ping
                    packet = inputStream.read();

                    if (!(packet instanceof PingPacket)) {
                        throw new IOException("Expected ping packet but got " + packet.getType());
                    }

                    // Write pong
                    outputStream.write(new PongPacket(((PingPacket) packet).getPayload()));

                    // Close connection
                    socket.close();

                    break;
                case 2:
                    // Switch to login state
                    inputStream.setConnectionState(ClientConnectionState.LOGIN);
                    outputStream.setConnectionState(ServerConnectionState.LOGIN);

                    // Accept login start
                    packet = inputStream.read();

                    if (!(packet instanceof LoginStartPacket)) {
                        throw new IOException("Expected login start packet but got " + packet.getType());
                    }

                    userName = ((LoginStartPacket) packet).getUserName();

                    outputStream.write(new LoginSuccessPacket(UUID.fromString("12a62593-a698-4499-9575-d3c47ea3683d"), ((LoginStartPacket) packet).getUserName()));

                    // Switch to play state
                    inputStream.setConnectionState(ClientConnectionState.PLAY);
                    outputStream.setConnectionState(ServerConnectionState.PLAY);

                    System.out.println("Wrote login success.");
                    outputStream.write(new JoinGamePacket(0, 1, 0, 0, 100, "flat", false));
                    System.out.println("Wrote join game.");
                    outputStream.write(new SpawnPositionPacket(new BlockPosition(0, 0, 0)));
                    System.out.println("Wrote spawn position.");
                    outputStream.write(new PlayerAbilitiesPacket(false, false, false, false, 1.3f, 1f));

                    // Keep alive
                    AtomicInteger lastKeepAliveId = new AtomicInteger();
                    AtomicLong lastKeepAliveResponseTime = new AtomicLong(System.currentTimeMillis());

                    // Keep alive task
                    new Thread(() -> {

                        try {
                            while (!socket.isClosed()) {
                                lastKeepAliveId.set((int) (Math.random() * 1000));

                                synchronized (outputStream) {
                                    outputStream.write(new KeepAliveRequestPacket(lastKeepAliveId.get()));
                                }

                                // Request again in 5 seconds
                                Thread.sleep(5000);

                                if (System.currentTimeMillis() - lastKeepAliveResponseTime.get() > 30000) {
                                    // Disconnect
                                    outputStream.write(new DisconnectPacket(GSON.toJson(new ChatMessage("Timed out"))));

                                    socket.close();
                                }
                            }
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }

                    }).start();

                    while (!socket.isClosed()) {
                        packet = inputStream.read();

                        System.out.println(packet);

                        if (packet instanceof ClientSettingsPacket) {
                            outputStream.write(new PlayerPositionAndLookPacket(0, 0, 0, 0f, 0f, (byte) 0x00, 0));
                        }
                        else if (packet instanceof KeepAliveResponsePacket) {
                            if (((KeepAliveResponsePacket) packet).getKeepAliveId() == lastKeepAliveId.get()) {
                                lastKeepAliveResponseTime.set(System.currentTimeMillis());
                            }
                        }
                        else if (packet instanceof ChatMessageRequestPacket) {
                            for (HandshakeConnection connection : marble.getPlayers()) {
                                connection.sendPacket(new ChatMessageResponsePacket(GSON.toJson(new ChatMessage(userName + ": " + ((ChatMessageRequestPacket) packet).getMessage())), (byte) 0));
                            }
                        }
                    }

                    break;
                default:
                    throw new IOException("Unknown status.");
            }
        } catch (IOException e) {
            System.out.println(socket.getInetAddress() + " disconnected: " + e.getMessage());

            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        marble.getPlayers().remove(this);
    }

    public void sendPacket(Packet packet) throws IOException {
        outputStream.write(packet);
    }

}
