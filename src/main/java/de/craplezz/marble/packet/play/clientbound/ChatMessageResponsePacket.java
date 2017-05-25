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
public class ChatMessageResponsePacket extends Packet {

    private String jsonMessage;
    private byte position;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        jsonMessage = inputStream.readString();
        position = inputStream.readByte();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeString(jsonMessage);
        outputStream.writeByte(position);
    }

    @Override
    public PacketType getType() {
        return PacketType.Play.ClientBound.CHAT_MESSAGE_RESPONSE;
    }

}
