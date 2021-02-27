package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.world.block.Block;

import java.io.IOException;

public class OutPacketBlockChange implements Packet<PacketHandler> {
    private final Block block;

    public OutPacketBlockChange(Block block) {
        this.block = block;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeLong(block.getPosition().toSerializedPosition());
        serializer.writeVarInt(block.getState().toGlobalId());
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
