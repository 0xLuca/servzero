package net.servzero.server.world.block;

import net.servzero.server.world.chunk.ChunkSection;

public class Block {
    private final ChunkSection section;
    private final Coordinate location;
    private final BlockState state;

    public Block(ChunkSection section, Coordinate location, Material material) {
        this.section = section;
        this.location = location;
        this.state = new BlockState(material.getId(), 0);
    }

    public ChunkSection getSection() {
        return section;
    }

    public Coordinate getLocation() {
        return location;
    }

    public BlockState getState() {
        return state;
    }
}
