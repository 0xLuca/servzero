package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.world.chunk.Chunk;

import java.io.IOException;


public class OutPacketChunkData implements Packet<PacketHandler> {
    private final Chunk chunk;

    public OutPacketChunkData(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        chunk.write(serializer);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
