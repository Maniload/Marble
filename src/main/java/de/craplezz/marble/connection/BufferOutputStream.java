package de.craplezz.marble.connection;

import de.craplezz.marble.util.BlockPosition;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Overload
 * @version 1.0
 */
public class BufferOutputStream extends DataOutputStream {

    public BufferOutputStream(OutputStream out) {
        super(out);
    }

    public void writeVarInt(int value) throws IOException {
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            write(temp);
        } while (value != 0);
    }

    public void writeString(String string) throws IOException {
        byte[] chars = string.getBytes();
        writeVarInt(chars.length);
        write(chars);
    }

    public void writePosition(BlockPosition position) throws IOException {
        writeLong(((position.getX() & 0x3FFFFFF) << 38) | ((position.getY() & 0xFFF) << 26) | (position.getZ() & 0x3FFFFFF));
    }

}
