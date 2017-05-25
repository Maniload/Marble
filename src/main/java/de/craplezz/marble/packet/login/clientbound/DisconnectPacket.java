package de.craplezz.marble.packet.login.clientbound;

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
public class DisconnectPacket extends Packet {

    private String message;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        message = inputStream.readString();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeString(message);
    }

    @Override
    public PacketType getType() {
        return PacketType.Login.ClientBound.DISCONNECT;
    }

}
