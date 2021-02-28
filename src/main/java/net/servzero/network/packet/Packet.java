package net.servzero.network.packet;

import net.servzero.network.packet.serialization.ISerializable;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public interface Packet<T extends PacketHandler> extends ISerializable<PacketDataSerializer> {
    void read(PacketDataSerializer serializer) throws IOException;
    void write(PacketDataSerializer serializer) throws IOException;
    void handle(T handler);
}
