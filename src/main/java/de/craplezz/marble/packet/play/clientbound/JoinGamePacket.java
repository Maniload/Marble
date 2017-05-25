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
public class JoinGamePacket extends Packet {

    private int entityId;
    private int gameMode;
    private int dimension;
    private int difficulty;
    private int maxPlayers;
    private String levelType;
    private boolean reducedDebugInfo;

    @Override
    public void read(BufferInputStream inputStream) throws IOException {
        entityId = inputStream.readInt();
        gameMode = inputStream.readUnsignedByte();
        dimension = inputStream.readInt();
        difficulty = inputStream.readUnsignedByte();
        maxPlayers = inputStream.readUnsignedByte();
        levelType = inputStream.readString();
        reducedDebugInfo = inputStream.readBoolean();
    }

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {
        outputStream.writeInt(entityId);
        outputStream.writeByte(gameMode);
        outputStream.writeInt(dimension);
        outputStream.writeByte(difficulty);
        outputStream.writeByte(maxPlayers);
        outputStream.writeString(levelType);
        outputStream.writeBoolean(reducedDebugInfo);
    }

    @Override
    public PacketType getType() {
        return PacketType.Play.ClientBound.JOIN_GAME;
    }

}
