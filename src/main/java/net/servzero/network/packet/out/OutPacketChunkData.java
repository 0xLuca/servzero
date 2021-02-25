package net.servzero.network.packet.out;

import com.flowpowered.nbt.*;
import com.flowpowered.nbt.stream.NBTOutputStream;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.Coordinate;
import net.servzero.server.world.chunk.ChunkSection;
import org.checkerframework.checker.units.qual.C;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OutPacketChunkData implements Packet<PacketHandler> {

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeInt(0); // Chunk X
        serializer.writeInt(0); // Chunk Y
        serializer.writeBoolean(true);  //Full chunk
        serializer.writeVarInt(1);  //Bitmask of chunk sections

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        NBTOutputStream os = new NBTOutputStream(outputStream, false);
        CompoundTag ct = new CompoundTag("",new CompoundMap());
        os.writeTag(ct);
        os.flush();
        os.close();
        serializer.writeBytes(outputStream.toByteArray());  //heightmaps

        serializer.writeVarInt(0); // Biomes array length
        // ...                      // Biomes array


        ChunkSection chunkSection = new ChunkSection();
        chunkSection.addBlock(new Coordinate(0, 0, 0), new Block());

        serializer.writeBytes(chunkSection.toByteArray());
        serializer.writeVarInt(0);
        return;
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
