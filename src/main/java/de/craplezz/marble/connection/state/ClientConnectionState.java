package de.craplezz.marble.connection.state;

import de.craplezz.marble.packet.PacketType;

/**
 * @author Overload
 * @version 1.0
 */
public enum ClientConnectionState implements ConnectionState {
    HANDSHAKE {

        @Override
        public PacketType getPacketType(int packetId) {
            return PacketType.Handshake.ServerBound.getPacketType(packetId);
        }

    },
    STATUS {

        @Override
        public PacketType getPacketType(int packetId) {
            return PacketType.Status.ServerBound.getPacketType(packetId);
        }

    },
    LOGIN {

        @Override
        public PacketType getPacketType(int packetId) {
            return PacketType.Login.ServerBound.getPacketType(packetId);
        }

    },
    PLAY {

        @Override
        public PacketType getPacketType(int packetId) {
            return PacketType.Play.ServerBound.getPacketType(packetId);
        }

    }
}
