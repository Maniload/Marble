package de.craplezz.marble.connection;

import de.craplezz.marble.util.BlockPosition;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

/**
 * @author Overload
 * @version 1.0
 */
public class BufferInputStream extends DataInputStream {

    public BufferInputStream(InputStream in) {
        super(in);
    }

    public int readVarInt() throws IOException {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = (byte) read();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public String readString() throws IOException {
        int length = readVarInt();
        byte[] chars = new byte[length];
        if (read(chars) < 0) {
            throw new EOFException();
        }
        return new String(chars);
    }

    public BlockPosition readPosition() throws IOException {
        byte[] b = new byte[8];
        readFully(b);
        BigInteger val = new BigInteger(b);

        return new BlockPosition(
                val.shiftRight(38).intValue(),
                val.shiftRight(26).and(BigInteger.valueOf(0x0FFF)).intValue(),
                val.shiftLeft(38).shiftLeft(38).intValue()
        );
    }

}
