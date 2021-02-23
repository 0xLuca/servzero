package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class InPacketKeepAlive implements Packet<AbstractInPacketPlayHandler> {
    private long id;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.id = serializer.readLong();
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handleKeepAlive(this);
    }
}
