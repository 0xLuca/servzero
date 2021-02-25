package net.servzero.server.world.chunk;

import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.Coordinate;

public class Chunk {
    private int x;
    private int z;
    private ChunkSection[] sections = new ChunkSection[16];

    public Chunk() {
        for (int i = 0; i < sections.length; i++ ) {
            sections[i] = new ChunkSection();
            sections[i].setY(i);
        }
    }

    public int addBlock(Coordinate coord, Block block) {
        if(Math.floor(coord.getX()/16.0) != x || Math.floor(coord.getZ()/16.0) != z) {
            //not in this chunk. big problem for them, not for us
            return -1;
        }
        block.setSection(sections[(int)Math.floor(coord.getY()/16.0)]);
        int chunkSelectionNum = (int) Math.floor(coord.getY()/16.0);
        ChunkSection section = sections[chunkSelectionNum];
        section.addBlock(coord, block);
        return 0;
    }
}
