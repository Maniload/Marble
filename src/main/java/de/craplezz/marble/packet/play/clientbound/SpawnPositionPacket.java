package de.craplezz.marble.packet.play.clientbound;

import de.craplezz.marble.connection.BufferInputStream;
import de.craplezz.marble.connection.BufferOutputStream;
import de.craplezz.marble.packet.Packet;
import de.craplezz.marble.packet.PacketType;
import de.craplezz.marble.util.BlockPosition;
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
public class SpawnPositionPacket extends Packet {

    private BlockPosition position;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        position = inputStream.readPosition();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writePosition(position);
    }

    @Override
    public PacketType getType() {
        return PacketType.Play.ClientBound.SPAWN_POSITION;
    }

}
