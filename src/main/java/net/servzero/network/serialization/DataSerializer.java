package net.servzero.network.serialization;

import net.servzero.network.packet.serialization.PacketDataSerializer;

public interface DataSerializer<T> {
    T read(PacketDataSerializer serializer);
    void write(PacketDataSerializer serializer, T value);
}
