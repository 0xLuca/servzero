package net.servzero.server.world.chunk;

import io.netty.buffer.Unpooled;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.BlockState;
import net.servzero.server.world.block.Coordinate;

import java.util.Arrays;

public class Chunk {
    private final int x;
    private final int z;
    private final int minXInclusive;
    private final int minZInclusive;
    private final int maxXExclusive;
    private final int maxZExclusive;
    private final ChunkSection[] sections = new ChunkSection[16];

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
        minXInclusive = this.x * 16;
        minZInclusive = this.z * 16;
        maxXExclusive = (this.x + 1) * 16;
        maxZExclusive = (this.z + 1) * 16;
        for (int i = 0; i < sections.length; i++) {
            sections[i] = new ChunkSection(i);
        }
    }

    public boolean isInChunk(Coordinate coordinate) {
        final int x = coordinate.getX();
        final int z = coordinate.getZ();
        return x >= this.minXInclusive && x < this.maxXExclusive && z <= this.minZInclusive && z < this.maxZExclusive;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int setBlock(Coordinate coord, BlockState state) {
        if (Math.floor(coord.getX() / 16.0) != x || Math.floor(coord.getZ() / 16.0) != z) {
            //not in this chunk. big problem for them, not for us
            return -1;
        }
        int chunkSelectionNum = (int) Math.floor(coord.getY() / 16.0);
        ChunkSection section = sections[chunkSelectionNum];
        //section.setBlock(coord, state); //TODO: calculate in-section coordinates from global coordinates
        return 0;
    }

    public int setChunkBlock(Coordinate coord, BlockState state) {
        int chunkSelectionNum = (int) Math.floor(coord.getY() / 16.0);
        ChunkSection section = sections[chunkSelectionNum];
        section.setBlock(coord, state);
        return 0;
    }

    public void write(PacketDataSerializer serializer) {
        serializer.writeInt(this.x); //chunkX
        serializer.writeInt(this.z); //chunkY
        serializer.writeBoolean(true); //groundupcontinous (new chunk drop all)
        serializer.writeVarInt(65535); //bitmask
        PacketDataSerializer buffer = new PacketDataSerializer(Unpooled.buffer());
        Arrays.stream(sections).forEach(section -> section.write(buffer));
        serializer.writeVarInt(buffer.readableBytes() + 256); //length
        Arrays.stream(sections).forEach(section -> section.write(serializer)); //data
        for (int i = 0; i < 256; i++) {
            serializer.writeByte(0);
        }
        serializer.writeVarInt(0); //biome array length
    }
}
