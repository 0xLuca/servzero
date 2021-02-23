package net.servzero.network.packet.in;

import net.servzero.logger.Logger;
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
        Logger.info("Got teleport confirm!");
    }

    public int getTeleportId() {
        return teleportId;
    }
}
