package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.InPacketStatusStartHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class InPacketStatusStart implements Packet<InPacketStatusStartHandler> {
    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {

    }

    @Override
    public void handle(InPacketStatusStartHandler handler) {
        handler.handle(this);
    }
}
