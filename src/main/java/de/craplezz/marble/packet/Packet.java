package de.craplezz.marble.packet;

import de.craplezz.marble.connection.BufferInputStream;
import de.craplezz.marble.connection.BufferOutputStream;

import java.io.IOException;

/**
 * @author Overload
 * @version 1.0
 */
public abstract class Packet {

    public abstract void read(BufferInputStream inputStream) throws IOException;

    public abstract void write(BufferOutputStream outputStream) throws IOException;

    public abstract PacketType getType();

}
