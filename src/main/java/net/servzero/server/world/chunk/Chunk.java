package net.servzero.server.world.chunk;

import io.netty.buffer.Unpooled;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.Coordinate;

import java.util.Arrays;

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

    public void write(PacketDataSerializer serializer) {
        serializer.writeInt(0); //chunkX
        serializer.writeInt(0); //chunkY
        serializer.writeBoolean(true); //groundupcontinous (new chunk drop all)
        serializer.writeVarInt(65535); //bitmask
        PacketDataSerializer buffer = new PacketDataSerializer(Unpooled.buffer());
        Arrays.stream(sections).forEach(section -> section.write(buffer));
        serializer.writeVarInt(buffer.readableBytes()); //length
        Arrays.stream(sections).forEach(section -> section.write(serializer)); //data
        serializer.writeVarInt(0); //biome array length
    }
}
