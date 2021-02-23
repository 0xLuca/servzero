package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class OutPacketHeldItemChange implements Packet<PacketHandler> {
    private final byte slot;

    public OutPacketHeldItemChange(int slot) {
        this.slot = (byte) slot;
    }

    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeByte(slot);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
