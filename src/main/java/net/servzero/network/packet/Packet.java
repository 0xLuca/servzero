package net.servzero.network.packet;

import net.servzero.network.packet.serialization.PacketDataSerializer;

public interface Packet<T extends PacketHandler> {
    void read(PacketDataSerializer serializer);
    void write(PacketDataSerializer serializer);
    void handle(T handler);
}
