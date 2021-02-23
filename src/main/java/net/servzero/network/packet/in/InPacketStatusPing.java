package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.InPacketStatusHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class InPacketStatusPing implements Packet<InPacketStatusHandler> {
    private long sentTime;

    @Override
    public void read(PacketDataSerializer serializer) {
        this.sentTime = serializer.readLong();
    }

    @Override
    public void write(PacketDataSerializer serializer) {

    }

    @Override
    public void handle(InPacketStatusHandler handler) {
        handler.handle(this);
    }

    public long getSentTime() {
        return sentTime;
    }
}
