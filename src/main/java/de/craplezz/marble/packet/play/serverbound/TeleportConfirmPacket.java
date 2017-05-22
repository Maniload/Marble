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
public class TeleportConfirmPacket extends Packet {

    private int teleportId;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        teleportId = inputStream.readVarInt();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeVarInt(teleportId);
    }

    @Override
    public PacketType getType() {
        return PacketType.Play.ServerBound.TELEPORT_CONFIRM;
    }

}
