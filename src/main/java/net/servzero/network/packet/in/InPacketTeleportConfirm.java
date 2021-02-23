package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class InPacketTeleportConfirm implements Packet<AbstractInPacketPlayHandler> {
    private int teleportId;

    @Override
    public void read(PacketDataSerializer serializer) {
        this.teleportId = serializer.readVarInt();
    }

    @Override
    public void write(PacketDataSerializer serializer) {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handleTeleportConfirm(this);
    }

    public int getTeleportId() {
        return teleportId;
    }
}
