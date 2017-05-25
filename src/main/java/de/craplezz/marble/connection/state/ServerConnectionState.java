package de.craplezz.marble.connection.state;

import de.craplezz.marble.packet.PacketType;

/**
 * @author Overload
 * @version 1.0
 */
public enum ServerConnectionState implements ConnectionState {
    STATUS {

        @Override
        public PacketType getPacketType(int packetId) {
            return PacketType.Status.ClientBound.getPacketType(packetId);
        }

    },
    LOGIN {

        @Override
        public PacketType getPacketType(int packetId) {
            return PacketType.Login.ClientBound.getPacketType(packetId);
        }

    },
    PLAY {

        @Override
        public PacketType getPacketType(int packetId) {
            return PacketType.Play.ClientBound.getPacketType(packetId);
        }

    }
}
