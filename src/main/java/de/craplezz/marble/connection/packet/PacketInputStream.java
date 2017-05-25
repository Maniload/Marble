package de.craplezz.marble.connection.packet;

import de.craplezz.marble.connection.BufferInputStream;
import de.craplezz.marble.connection.state.ConnectionState;
import de.craplezz.marble.packet.Packet;
import de.craplezz.marble.packet.PacketType;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * @author Overload
 * @version 1.0
 */
public class PacketInputStream {

    private final BufferInputStream inputStream;

    @Getter
    @Setter
    private ConnectionState connectionState;

    public PacketInputStream(BufferInputStream inputStream, ConnectionState connectionState) {
        this.inputStream = inputStream;
        this.connectionState = connectionState;
    }

    public Packet read() throws IOException {
        int packetLength = inputStream.readVarInt();

        // Read full packet
        byte[] buffer = new byte[packetLength];
        if (inputStream.read(buffer) < 0) {
            throw new EOFException();
        }
        ByteArrayInputStream byteArray = new ByteArrayInputStream(buffer);

        // Make it readable
        BufferInputStream packetInputStream = new BufferInputStream(byteArray);

        // Determine packet type
        int packetId = packetInputStream.readVarInt();
        PacketType packetType = connectionState.getPacketType(packetId);
        if (packetType == null) {
            System.out.println("Unknown packet with id: " + packetId);

            return null;
        }

        // Create packet object
        Packet packet = packetType.createInstance();

        // Read packet data
        packet.read(packetInputStream);

        return packet;
    }

}
