package de.craplezz.marble.packet.login.serverbound;

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
public class LoginStartPacket extends Packet {

    private String userName;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        userName = inputStream.readString();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeString(userName);
    }

    @Override
    public PacketType getType() {
        return PacketType.Login.ServerBound.LOGIN_START;
    }

}
