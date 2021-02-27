package net.servzero.server.world.chunk;

import java.util.Objects;

public class ChunkCoordinate {
    private final int chunkX;
    private final int chunkZ;

    private ChunkCoordinate(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public static ChunkCoordinate get(int chunkX, int chunkZ) {
        return new ChunkCoordinate(chunkX, chunkZ);
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkCoordinate that = (ChunkCoordinate) o;
        return chunkX == that.chunkX && chunkZ == that.chunkZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chunkX, chunkZ);
    }
}
