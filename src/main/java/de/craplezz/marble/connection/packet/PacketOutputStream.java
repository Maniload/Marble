package de.craplezz.marble.connection.packet;

import de.craplezz.marble.connection.BufferOutputStream;
import de.craplezz.marble.connection.state.ConnectionState;
import de.craplezz.marble.packet.Packet;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Overload
 * @version 1.0
 */
public class PacketOutputStream {

    private final BufferOutputStream outputStream;

    @Getter
    @Setter
    private ConnectionState connectionState;

    public PacketOutputStream(BufferOutputStream outputStream, ConnectionState connectionState) {
        this.outputStream = outputStream;
        this.connectionState = connectionState;
    }

    public void write(Packet packet) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        BufferOutputStream packetOutputStream = new BufferOutputStream(byteArray);

        packetOutputStream.writeVarInt(packet.getType().getPacketId());

        // Write packet data to buffer
        packet.write(packetOutputStream);

        // Write packet length
        outputStream.writeVarInt(packetOutputStream.size());

        // Write buffer to stream
        byteArray.writeTo(outputStream);
    }

}
