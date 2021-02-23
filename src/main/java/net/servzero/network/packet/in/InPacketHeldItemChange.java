package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class InPacketHeldItemChange implements Packet<AbstractInPacketPlayHandler> {
    private short slot;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.slot = serializer.readShort();
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handleHeldItemChange(this);
    }

    public short getSlot() {
        return slot;
    }
}
