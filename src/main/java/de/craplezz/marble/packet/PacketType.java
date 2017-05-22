package de.craplezz.marble.packet;

import de.craplezz.marble.packet.handshake.serverbound.HandshakePacket;
import de.craplezz.marble.packet.login.clientbound.DisconnectPacket;
import de.craplezz.marble.packet.login.clientbound.LoginSuccessPacket;
import de.craplezz.marble.packet.login.serverbound.LoginStartPacket;
import de.craplezz.marble.packet.play.clientbound.*;
import de.craplezz.marble.packet.play.serverbound.ChatMessageRequestPacket;
import de.craplezz.marble.packet.play.serverbound.ClientSettingsPacket;
import de.craplezz.marble.packet.play.serverbound.KeepAliveResponsePacket;
import de.craplezz.marble.packet.play.serverbound.TeleportConfirmPacket;
import de.craplezz.marble.packet.status.clientbound.PongPacket;
import de.craplezz.marble.packet.status.clientbound.ResponsePacket;
import de.craplezz.marble.packet.status.serverbound.PingPacket;
import de.craplezz.marble.packet.status.serverbound.RequestPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Overload
 * @version 1.0
 */
public interface PacketType {

    int getPacketId();

    Packet createInstance();

    interface Handshake {

        @Getter
        @RequiredArgsConstructor
        enum ServerBound implements PacketType {
            HANDSHAKE (0x00) {

                @Override
                public Packet createInstance() {
                    return new HandshakePacket();
                }

            };

            private final int packetId;

            public static ServerBound getPacketType(int packetId) {
                for (ServerBound packetType : values()) {
                    if (packetType.getPacketId() == packetId) {
                        return packetType;
                    }
                }
                return null;
            }

        }

    }

    interface Status {

        @Getter
        @RequiredArgsConstructor
        enum ServerBound implements PacketType {
            REQUEST (0x00) {

                @Override
                public Packet createInstance() {
                    return new RequestPacket();
                }

            },
            PING (0x01) {

                @Override
                public Packet createInstance() {
                    return new PingPacket();
                }

            };

            private final int packetId;

            public static ServerBound getPacketType(int packetId) {
                for (ServerBound packetType : values()) {
                    if (packetType.getPacketId() == packetId) {
                        return packetType;
                    }
                }
                return null;
            }

        }

        @Getter
        @RequiredArgsConstructor
        enum ClientBound implements PacketType {
            RESPONSE (0x00) {

                @Override
                public Packet createInstance() {
                    return new ResponsePacket();
                }

            },
            PONG (0x01) {

                @Override
                public Packet createInstance() {
                    return new PongPacket();
                }

            };

            private final int packetId;

            public static ClientBound getPacketType(int packetId) {
                for (ClientBound packetType : values()) {
                    if (packetType.getPacketId() == packetId) {
                        return packetType;
                    }
                }
                return null;
            }

        }

    }

    interface Login {

        @Getter
        @RequiredArgsConstructor
        enum ServerBound implements PacketType {
            LOGIN_START (0x00) {

                @Override
                public Packet createInstance() {
                    return new LoginStartPacket();
                }

            };

            private final int packetId;

            public static ServerBound getPacketType(int packetId) {
                for (ServerBound packetType : values()) {
                    if (packetType.getPacketId() == packetId) {
                        return packetType;
                    }
                }
                return null;
            }

        }

        @Getter
        @RequiredArgsConstructor
        enum ClientBound implements PacketType {
            DISCONNECT (0x00) {

                @Override
                public Packet createInstance() {
                    return new DisconnectPacket();
                }

            },
            LOGIN_SUCCESS (0x02) {

                @Override
                public Packet createInstance() {
                    return new LoginSuccessPacket();
                }

            };

            private final int packetId;

            public static ClientBound getPacketType(int packetId) {
                for (ClientBound packetType : values()) {
                    if (packetType.getPacketId() == packetId) {
                        return packetType;
                    }
                }
                return null;
            }

        }

    }

    interface Play {

        @Getter
        @RequiredArgsConstructor
        enum ServerBound implements PacketType {
            CLIENT_SETTINGS (0x04) {

                @Override
                public Packet createInstance() {
                    return new ClientSettingsPacket();
                }

            },
            PLAYER_POSITION_AND_LOOK (0x0D) {

                @Override
                public Packet createInstance() {
                    return new de.craplezz.marble.packet.play.serverbound.PlayerPositionAndLookPacket();
                }

            },
            TELEPORT_CONFIRM (0x00) {

                @Override
                public Packet createInstance() {
                    return new TeleportConfirmPacket();
                }

            },
            KEEP_ALIVE_RESPONSE (0x0B) {

                @Override
                public Packet createInstance() {
                    return new KeepAliveResponsePacket();
                }

            },
            CHAT_MESSAGE_REQUEST (0x02) {

                @Override
                public Packet createInstance() {
                    return new ChatMessageRequestPacket();
                }

            };

            private final int packetId;

            public static ServerBound getPacketType(int packetId) {
                for (ServerBound packetType : values()) {
                    if (packetType.getPacketId() == packetId) {
                        return packetType;
                    }
                }
                return null;
            }

        }

        @Getter
        @RequiredArgsConstructor
        enum ClientBound implements PacketType {
            JOIN_GAME (0x23) {

                @Override
                public Packet createInstance() {
                    return new JoinGamePacket();
                }

            },
            SPAWN_POSITION (0x43) {

                @Override
                public Packet createInstance() {
                    return new SpawnPositionPacket();
                }

            },
            PLAYER_ABILITIES (0x2B) {

                @Override
                public Packet createInstance() {
                    return new PlayerAbilitiesPacket();
                }

            },
            PLAYER_POSITION_AND_LOOK (0x2E) {

                @Override
                public Packet createInstance() {
                    return new PlayerPositionAndLookPacket();
                }

            },
            KEEP_ALIVE_REQUEST (0x1F) {

                @Override
                public Packet createInstance() {
                    return new KeepAliveRequestPacket();
                }

            },
            CHAT_MESSAGE_RESPONSE (0x0F) {

                @Override
                public Packet createInstance() {
                    return new ChatMessageResponsePacket();
                }

            };

            private final int packetId;

            public static ClientBound getPacketType(int packetId) {
                for (ClientBound packetType : values()) {
                    if (packetType.getPacketId() == packetId) {
                        return packetType;
                    }
                }
                return null;
            }

        }

    }

}
