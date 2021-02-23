package net.servzero.server.world.chunk;

import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class ChunkSection {
    private int y;
    private short blockCount;
    private short bitsPerBlock;
    private Map<Coordinate, Block> blockMap = new HashMap<>();

    public ChunkSection() {
    }

    public int addBlock(Coordinate coord, Block block) {
        blockMap.put(coord, block);
        return 0;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
