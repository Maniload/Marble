package de.craplezz.marble.packet.login.clientbound;

import de.craplezz.marble.connection.BufferInputStream;
import de.craplezz.marble.connection.BufferOutputStream;
import de.craplezz.marble.packet.Packet;
import de.craplezz.marble.packet.PacketType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Overload
 * @version 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessPacket extends Packet {

    private UUID uniqueId;
    private String userName;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        uniqueId = UUID.fromString(inputStream.readString());
        userName = inputStream.readString();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeString(uniqueId.toString());
        outputStream.writeString(userName);
    }

    @Override
    public PacketType getType() {
        return PacketType.Login.ClientBound.LOGIN_SUCCESS;
    }

}
