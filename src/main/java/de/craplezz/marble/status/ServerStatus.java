package de.craplezz.marble.status;

import de.craplezz.marble.util.Streams;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Base64;

/**
 * @author Overload
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public class ServerStatus implements Serializable {

    private Version version;
    private Players players;
    private Description description;
    private String favicon;

    public ServerStatus(Version version, Players players, Description description, Favicon favicon) throws IOException {
        this(version, players, description, favicon.getResource());
    }

    public ServerStatus(Version version, Players players, Description description) {
        this(version, players, description, (String) null);
    }

    @Getter
    @AllArgsConstructor
    public static class Version implements Serializable {

        private String name;
        private int protocol;

    }

    @Getter
    @AllArgsConstructor
    public static class Players implements Serializable {

        private int max;
        private int online;
        private SampleItem[] sample;

        public Players(int max, int online) {
            this(max, online, null);
        }

        @Getter
        @AllArgsConstructor
        public static class SampleItem implements Serializable {

            private String name;
            private String id;

        }

    }

    // TODO: Add chat api
    @Getter
    @AllArgsConstructor
    public static class Description implements Serializable {

        private String text;

    }

    @Getter
    public static class Favicon {

        private String resource;

        public Favicon(URL url) throws IOException {
            this.resource = "data:image/png;base64," + Base64.getEncoder().encodeToString(Streams.readFully(url.openConnection()));
        }

    }

}
