package net.servzero.network.packet;

import net.servzero.network.packet.in.InPacketHandshakeSetProtocol;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;

public enum EnumPacket {
    HANDSHAKE(0x00, InPacketHandshakeSetProtocol.class)
    ;

    public static Optional<? extends Packet> getPacketById(int id) {
        return Arrays.stream(values()).filter(enumPacket -> enumPacket.packetId == id).map(EnumPacket::getImplementation).map(packetClass -> {
            try {
                return packetClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }).findFirst();
    }

    public static OptionalInt getIdByPacket(Packet<?> packet) {
        return Arrays.stream(values()).filter(enumPacket -> enumPacket.getImplementation().equals(packet.getClass())).mapToInt(EnumPacket::getPacketId).findFirst();
    }

    private final int packetId;
    private final Class<? extends Packet> implementation;

    EnumPacket(int packetId, Class<? extends Packet> implementation) {
        this.packetId = packetId;
        this.implementation = implementation;
    }

    public int getPacketId() {
        return packetId;
    }

    public Class<? extends Packet> getImplementation() {
        return implementation;
    }
}
