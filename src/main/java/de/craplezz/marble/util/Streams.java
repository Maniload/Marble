package de.craplezz.marble.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * @author Overload
 * @version 1.0
 */
@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class Streams {

    public static byte[] readFully(InputStream inputStream, int length) throws IOException {
        byte[] b = new byte[length];
        if (inputStream.read(b) < 0) {
            throw new EOFException();
        }
        return b;
    }

    public static byte[] readFully(URLConnection urlConnection) throws IOException {
        return readFully(urlConnection.getInputStream(), urlConnection.getContentLength());
    }

}
