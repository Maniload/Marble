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
public class PlayerAbilitiesPacket extends Packet {

    private boolean invulnerable, flying, allowFlying, creativeMode;
    private float flyingSpeed, fieldOfView;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        byte flags = inputStream.readByte();
        invulnerable = (flags & 0x01) == 0x01;
        flying = (flags & 0x02) == 0x02;
        allowFlying = (flags & 0x04) == 0x04;
        creativeMode = (flags & 0x08) == 0x08;
        flyingSpeed = inputStream.readFloat();
        fieldOfView = inputStream.readFloat();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        byte flags = 0x00;
        if (invulnerable) {
            flags &= 0x01;
        }
        if (flying) {
            flags &= 0x02;
        }
        if (allowFlying) {
            flags &= 0x04;
        }
        if (creativeMode) {
            flags &= 0x08;
        }
        outputStream.writeByte(flags);
        outputStream.writeFloat(flyingSpeed);
        outputStream.writeFloat(fieldOfView);
    }

    @Override
    public PacketType getType() {
        return PacketType.Play.ClientBound.PLAYER_ABILITIES;
    }

}
