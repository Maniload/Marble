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
public class ClientSettingsPacket extends Packet {

    private String locale;
    private byte viewDistance;
    private int chatMode;
    private boolean chatColorsEnabled;
    private int displayedSkinParts;
    private int mainHand;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        locale = inputStream.readString();
        viewDistance = inputStream.readByte();
        chatMode = inputStream.readVarInt();
        chatColorsEnabled = inputStream.readBoolean();
        displayedSkinParts = inputStream.readUnsignedByte();
        mainHand = inputStream.readVarInt();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeString(locale);
        outputStream.writeByte(viewDistance);
        outputStream.writeVarInt(chatMode);
        outputStream.writeBoolean(chatColorsEnabled);
        outputStream.writeByte(displayedSkinParts);
        outputStream.writeVarInt(mainHand);
    }

    @Override
    public PacketType getType() {
        return PacketType.Play.ServerBound.CLIENT_SETTINGS;
    }

}
