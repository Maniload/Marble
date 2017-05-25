package de.craplezz.marble;

import de.craplezz.marble.connection.BufferInputStream;
import de.craplezz.marble.connection.BufferOutputStream;
import de.craplezz.marble.connection.packet.PacketInputStream;
import de.craplezz.marble.connection.packet.PacketOutputStream;
import de.craplezz.marble.connection.state.ClientConnectionState;
import de.craplezz.marble.connection.state.ServerConnectionState;
import de.craplezz.marble.packet.Packet;
import de.craplezz.marble.packet.handshake.serverbound.HandshakePacket;
import de.craplezz.marble.packet.login.clientbound.LoginSuccessPacket;
import de.craplezz.marble.packet.login.serverbound.LoginStartPacket;
import de.craplezz.marble.packet.play.clientbound.JoinGamePacket;
import de.craplezz.marble.packet.play.clientbound.SpawnPositionPacket;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Overload
 * @version 1.0
 */
public class MarbleClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 25565);

            PacketInputStream inputStream = new PacketInputStream(new BufferInputStream(socket.getInputStream()), ServerConnectionState.STATUS);
            PacketOutputStream outputStream = new PacketOutputStream(new BufferOutputStream(socket.getOutputStream()), ClientConnectionState.HANDSHAKE);

            outputStream.write(new HandshakePacket(47, "localhost", 25565, 2));

            outputStream.setConnectionState(ClientConnectionState.LOGIN);
            inputStream.setConnectionState(ServerConnectionState.LOGIN);

            outputStream.write(new LoginStartPacket("Overload"));

            Packet packet;

            while (!socket.isClosed()) {
                packet = inputStream.read();

                if (packet instanceof LoginSuccessPacket) {
                    outputStream.setConnectionState(ClientConnectionState.PLAY);
                    inputStream.setConnectionState(ServerConnectionState.PLAY);
                }
                else if (packet instanceof JoinGamePacket) {
                    JoinGamePacket joinGamePacket = (JoinGamePacket) packet;
                    System.out.println(joinGamePacket.getEntityId());
                    System.out.println(joinGamePacket.getGameMode());
                    System.out.println(joinGamePacket.getDimension());
                    System.out.println(joinGamePacket.getDifficulty());
                    System.out.println(joinGamePacket.getMaxPlayers());
                    System.out.println(joinGamePacket.getLevelType());
                    System.out.println(joinGamePacket.isReducedDebugInfo());
                }
                else if (packet instanceof SpawnPositionPacket) {
                    SpawnPositionPacket spawnPositionPacket = (SpawnPositionPacket) packet;
                    System.out.println("X: " + spawnPositionPacket.getPosition().getX());
                    System.out.println("Y: " + spawnPositionPacket.getPosition().getY());
                    System.out.println("Z: " + spawnPositionPacket.getPosition().getZ());
                }

                System.out.println(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
