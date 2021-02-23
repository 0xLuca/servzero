package net.servzero.server.world;

import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.Coordinate;
import net.servzero.server.world.chunk.Chunk;

import java.util.HashMap;
import java.util.Map;

public class World {
    private Map<Coordinate, Chunk> chunkMap = new HashMap<>();
    public void generate(Coordinate coord) {
        //TODO: Implement world generation

        for (int i = 0; i < 10; i ++) {
            Chunk chunk1 = new Chunk();
            Coordinate chunkCoord = coord.toChunkCoord();
            if(!chunkMap.containsKey(chunkCoord))
                chunkMap.put(chunkCoord, chunk1);

            Block block = new Block();
            Chunk chunk = chunkMap.get(chunkCoord);
            assert chunk != null;
            chunk1.addBlock(new Coordinate(i, 0), block);
        }
    }
}
