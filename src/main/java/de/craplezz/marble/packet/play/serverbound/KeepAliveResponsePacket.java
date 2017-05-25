package de.craplezz.marble.packet.play.serverbound;

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
public class KeepAliveResponsePacket extends Packet {

    private int keepAliveId;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        keepAliveId = inputStream.readVarInt();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeVarInt(keepAliveId);
    }

    @Override
    public PacketType getType() {
        return PacketType.Play.ServerBound.KEEP_ALIVE_RESPONSE;
    }

}
