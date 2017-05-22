package de.craplezz.marble.connection.state;

import de.craplezz.marble.packet.PacketType;

/**
 * @author Overload
 * @version 1.0
 */
public interface ConnectionState {

    PacketType getPacketType(int packetId);

}
