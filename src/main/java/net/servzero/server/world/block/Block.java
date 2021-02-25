package net.servzero.server.world.block;

import net.servzero.server.world.chunk.ChunkSection;

public class Block {
    private  ChunkSection section;
    private final Coordinate location;
    private final BlockState state;

    public Block(Coordinate location, Material material) {
        //this.section = section;
        this.location = location;
        this.state = new BlockState(material.getId(), 0);
    }

    public ChunkSection getSection() {
        return section;
    }

    public void setSection(ChunkSection section) { this.section = section;}

    public Coordinate getLocation() {
        return location;
    }

    public BlockState getState() {
        return state;
    }
}
