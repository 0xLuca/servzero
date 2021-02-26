package net.servzero.server.world.chunk;

import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.Coordinate;

import java.io.IOException;
import java.util.*;

public class ChunkSection {
    private int y;
    private short blockCount;
    private short bitsPerBlock;
    private Map<Coordinate, Block> blockMap = new HashMap<>();

    public ChunkSection() {
    }

    public int getBlockCount() {
        return blockMap.size();
    }

    public void write(PacketDataSerializer serializer) {
        serializer.writeByte(13);
        serializer.writeVarInt(0);
        long[] blocks = new long[4096];
        long stone = (1 << 4) | 0;
        Arrays.fill(blocks, stone);
        for (int i = 0; i < blocks.length * 2; i++) {
            serializer.writeByte(0);
        }
    }

    public void addBlock(Coordinate coord, Block block) {
        blockMap.put(coord, block);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
