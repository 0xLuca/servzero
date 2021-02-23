package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class OutPacketStatusPong implements Packet<PacketHandler> {
    private long sentTime;

    public OutPacketStatusPong(long sentTime) {
        this.sentTime = sentTime;
    }

    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeLong(this.sentTime);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
