package de.craplezz.marble.packet.status.serverbound;

import de.craplezz.marble.connection.BufferInputStream;
import de.craplezz.marble.connection.BufferOutputStream;
import de.craplezz.marble.packet.Packet;
import de.craplezz.marble.packet.PacketType;

import java.io.IOException;

/**
 * @author Overload
 * @version 1.0
 */
public class RequestPacket extends Packet {

    @Override
    public void read(BufferInputStream inputStream) throws IOException {}

    @Override
    public void write(BufferOutputStream outputStream) throws IOException {}

    @Override
    public PacketType getType() {
        return PacketType.Status.ServerBound.REQUEST;
    }

}
