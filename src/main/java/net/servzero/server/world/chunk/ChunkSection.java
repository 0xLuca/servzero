package net.servzero.server.world.chunk;

import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.world.block.BlockState;
import net.servzero.server.world.block.Coordinate;

public class ChunkSection {
    private static final int BITS_PER_BLOCK = 13;

    private int y;
    private final BlockState[][][] blockStates = new BlockState[16][16][16];

    public ChunkSection(int y) {
        this.y = y;
    }

    public void write(PacketDataSerializer serializer) {
        serializer.writeByte(BITS_PER_BLOCK);
        serializer.writeVarInt(0);

        long[] data = new long[16 * 16 * 16 * BITS_PER_BLOCK / 64];

        for (int y = 0; y < 16; y++) {
            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {
                    int blockNumber = (((y * 16) + z) * 16) + x;
                    int startLong = (blockNumber * BITS_PER_BLOCK) / 64;
                    int startOffset = (blockNumber * BITS_PER_BLOCK) % 64;
                    int endLong = ((blockNumber + 1) * BITS_PER_BLOCK - 1) / 64;

                    BlockState state = blockStates[x][y][z];
                    int value = (state == null ? BlockState.EMPTY : state).toGlobalId();

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

    public void setBlock(Coordinate coord, BlockState blockState) {
        blockStates[coord.getX()][coord.getY()][coord.getZ()] = blockState;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
