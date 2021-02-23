package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class InPacketKeepAlive implements Packet<AbstractInPacketHandler> {
    private long id;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.id = serializer.readLong();
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketHandler handler) {
        handler.handleKeepAlive(this);
    }
}
