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
}
