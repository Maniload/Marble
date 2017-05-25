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
public class PlayerPositionAndLookPacket extends Packet {

    private double x, y, z;
    private float yaw, pitch;
    private byte flags;
    private int teleportId;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        x = inputStream.readDouble();
        y = inputStream.readDouble();
        z = inputStream.readDouble();
        yaw = inputStream.readFloat();
        pitch = inputStream.readFloat();
        flags = inputStream.readByte();
        teleportId = inputStream.readVarInt();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeDouble(x);
        outputStream.writeDouble(y);
        outputStream.writeDouble(z);
        outputStream.writeFloat(yaw);
        outputStream.writeFloat(pitch);
        outputStream.writeByte(flags);
        outputStream.writeVarInt(teleportId);
    }

    @Override
    public PacketType getType() {
        return PacketType.Play.ClientBound.PLAYER_POSITION_AND_LOOK;
    }

}
