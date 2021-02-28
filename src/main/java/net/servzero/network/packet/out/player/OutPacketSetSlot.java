package net.servzero.network.packet.out.player;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.inventory.ItemStack;

import java.io.IOException;

public class OutPacketSetSlot implements Packet<PacketHandler> {
    private final byte windowId;
    private final short slotId;
    private final ItemStack stack;

    public OutPacketSetSlot(int windowId, int slotId, ItemStack stack) {
        this.windowId = (byte) windowId;
        this.slotId = (short) slotId;
        this.stack = stack;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeByte(this.windowId);
        serializer.writeShort(this.slotId);
        serializer.writeItemStack(this.stack);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
