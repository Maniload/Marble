package de.craplezz.marble.packet.play.clientbound;

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
public class ChunkDataPacket extends Packet {

    private int chunkX, chunkY;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {

    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {

    }

    @Override
    public PacketType getType() {
        return null;
    }

}
