package net.servzero.server.world.chunk;

import io.netty.buffer.Unpooled;
import net.servzero.helper.Validator;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.world.World;
import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.BlockState;
import net.servzero.server.world.block.Position;

import java.util.Arrays;

public class Chunk {

    private final World world;
    private final ChunkCoordinate coordinate;
    private final int minXInclusive;
    private final int minZInclusive;
    private final int maxXExclusive;
    private final int maxZExclusive;
    private final ChunkSection[] sections = new ChunkSection[16];

    public Chunk(World world, int x, int z) {
        this.world = world;
        this.coordinate = ChunkCoordinate.get(x, z);
        this.minXInclusive = this.coordinate.getChunkX() * 16;
        this.minZInclusive = this.coordinate.getChunkZ() * 16;
        this.maxXExclusive = (this.coordinate.getChunkX() + 1) * 16;
        this.maxZExclusive = (this.coordinate.getChunkZ() + 1) * 16;
        for (int i = 0; i < sections.length; i++) {
            this.sections[i] = new ChunkSection(this, i);
        }
    }

    public ChunkCoordinate getCoordinate() {
        return this.coordinate;
    }

    public World getWorld() {
        return this.world;
    }

    public boolean isInChunk(Position position) {
        final int x = position.getX();
        final int z = position.getZ();
        return x >= this.minXInclusive && x < this.maxXExclusive && z <= this.minZInclusive && z < this.maxZExclusive;
    }

    public int getX() {
        return this.coordinate.getChunkX();
    }

    public int getZ() {
        return this.coordinate.getChunkZ();
    }

    private ChunkSection getSectionFromY(int y) {
        int sectionNum = (int) Math.floor(y / 16.0D);
        return this.sections[sectionNum];
    }

    public Block getBlockAt(Position position) {
        Validator.validateCoordinate(position);
        ChunkSection section = getSectionFromY(position.getY());
        return section.getBlockAt(position);
    }

    public void setChunkBlock(Position position, BlockState state) {
        Validator.validateCoordinate(position);
        ChunkSection section = getSectionFromY(position.getY());
        section.setBlock(position, state);
    }

    public void write(PacketDataSerializer serializer) {
        serializer.writeInt(this.coordinate.getChunkX()); //chunkX
        serializer.writeInt(this.coordinate.getChunkZ()); //chunkZ
        serializer.writeBoolean(true); //groundupcontinous (new chunk drop all)
        serializer.writeVarInt(65535); //bitmask
        PacketDataSerializer buffer = new PacketDataSerializer(Unpooled.buffer());
        Arrays.stream(this.sections).forEach(section -> section.write(buffer));
        serializer.writeVarInt(buffer.readableBytes() + 256); //length
        Arrays.stream(this.sections).forEach(section -> section.write(serializer)); //data
        for (int i = 0; i < 256; i++) {
            serializer.writeByte(0);
        }
        serializer.writeVarInt(0); //biome array length
    }
}
