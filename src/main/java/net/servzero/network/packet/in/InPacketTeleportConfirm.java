package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class InPacketTeleportConfirm implements Packet<AbstractInPacketHandler> {
    private int teleportId;

    @Override
    public void read(PacketDataSerializer serializer) {
        this.teleportId = serializer.readVarInt();
    }

    @Override
    public void write(PacketDataSerializer serializer) {

    }

    @Override
    public void handle(AbstractInPacketHandler handler) {
        handler.handleTeleportConfirm(this);
    }

    public int getTeleportId() {
        return teleportId;
    }
}
