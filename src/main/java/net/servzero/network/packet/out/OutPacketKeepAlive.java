package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;
import java.util.Random;

public class OutPacketKeepAlive implements Packet<PacketHandler> {
    private static final Random ID_GENERATOR = new Random();

    private final long id = ID_GENERATOR.nextLong();

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeLong(id);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
