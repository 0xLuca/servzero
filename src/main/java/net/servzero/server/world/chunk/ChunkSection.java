package net.servzero.server.world.chunk;

import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.Coordinate;

import java.io.IOException;
import java.util.*;

public class ChunkSection {
    private static final int BITS_PER_BLOCK = 13;

    private int y;
    private short blockCount;
    private Map<Coordinate, Block> blockMap = new HashMap<>();

    public ChunkSection() {
    }

    public int getBlockCount() {
        return blockMap.size();
    }

    public void write(PacketDataSerializer serializer) {
        serializer.writeByte(BITS_PER_BLOCK);
//        serializer.writeByte(16);
        serializer.writeVarInt(0);
        long[] data = new long[16 * 16 * 16 * BITS_PER_BLOCK / 64];

        int stone = (1 << 4) | 0;

        for (int y = 0; y < 16; y++) {
            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {
                    int blockNumber = (((y * 16) + z) * 16) + x;
                    int startLong = (blockNumber * BITS_PER_BLOCK) / 64;
                    int startOffset = (blockNumber * BITS_PER_BLOCK) % 64;
                    int endLong = ((blockNumber + 1) * BITS_PER_BLOCK - 1) / 64;

                    int value = y < 10 ? stone : 0;

                    data[startLong] |= ((long) value << startOffset);

                    if (startLong != endLong) {
                        data[endLong] = (value >> (64 - startOffset));
                    }
                }
            }
        }

        serializer.writeLongArray(data);
        for (int i = 0; i < 4096; i++) {
            serializer.writeByte(1);
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
