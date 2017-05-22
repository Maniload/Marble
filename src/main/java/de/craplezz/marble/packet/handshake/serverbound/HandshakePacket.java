package de.craplezz.marble.packet.handshake.serverbound;

import de.craplezz.marble.connection.BufferInputStream;
import de.craplezz.marble.connection.BufferOutputStream;
import de.craplezz.marble.packet.Packet;
import de.craplezz.marble.packet.PacketType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * @author Overload
 * @version 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HandshakePacket extends Packet {

    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private int nextStatus;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        protocolVersion = inputStream.readVarInt();
        serverAddress = inputStream.readString();
        serverPort = inputStream.readUnsignedShort();
        nextStatus = inputStream.readVarInt();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeVarInt(protocolVersion);
        outputStream.writeString(serverAddress);
        outputStream.writeShort(serverPort);
        outputStream.writeVarInt(nextStatus);
    }

    @Override
    public PacketType getType() {
        return PacketType.Handshake.ServerBound.HANDSHAKE;
    }

}
