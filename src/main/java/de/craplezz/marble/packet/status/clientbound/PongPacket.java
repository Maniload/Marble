package de.craplezz.marble.packet.status.clientbound;

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
public class PongPacket extends Packet {

    private long payload;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        payload = inputStream.readLong();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeLong(payload);
    }

    @Override
    public PacketType getType() {
        return PacketType.Status.ClientBound.PONG;
    }

}
