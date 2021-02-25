package net.servzero.server.world.chunk;

import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.Coordinate;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkSection {
    private int y;
    private short blockCount;
    private short bitsPerBlock;
    private Map<Coordinate, Block> blockMap = new HashMap<>();

    public ChunkSection() {
    }

    public int getBlockCount() {
        return blockMap.size();
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        List<Byte> returnByteList = new ArrayList<>();

        dos.writeShort(getBlockCount());
        dos.writeByte(14);

        dos.writeInt(1);
        dos.writeLong(1111111);
        dos.flush();


        return  bos.toByteArray();
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
