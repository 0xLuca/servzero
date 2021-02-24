package net.servzero.network.packet.out.entity;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;
import java.util.Arrays;

public class OutPacketDestroyEntities implements Packet<PacketHandler> {
    private final int[] entityIds;

    public OutPacketDestroyEntities(int... entityIds) {
        this.entityIds = entityIds;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeVarInt(entityIds.length);
        Arrays.stream(entityIds).forEach(serializer::writeVarInt);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
