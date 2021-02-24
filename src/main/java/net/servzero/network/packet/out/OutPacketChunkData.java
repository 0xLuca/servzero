package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class OutPacketChunkData implements Packet<PacketHandler> {
    private byte[] data = new byte[] {};

    public OutPacketChunkData() {
        Properties properties = new Properties();
        InputStream fileStream = getClass().getResourceAsStream("/bytes.properties");
        try {
            properties.load(fileStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        String data = properties.getProperty("data");
        String[] split = data.split(", ");
        this.data = new byte[split.length];
        for (int i = 0; i < split.length; i++) {
            this.data[i] = Byte.parseByte(split[i]);
        }
        System.out.println("DATA LOADED!");
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeInt(0);
        serializer.writeInt(0);
        serializer.writeBoolean(true);
        serializer.writeVarInt(63);
        serializer.writeVarInt(data.length);
        serializer.writeBytes(data);
        serializer.writeVarInt(0);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
