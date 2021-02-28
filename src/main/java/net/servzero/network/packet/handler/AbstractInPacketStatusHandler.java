package net.servzero.network.packet.handler;

import net.servzero.network.NetworkHandler;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.in.InPacketStatusPing;
import net.servzero.network.packet.in.InPacketStatusStart;

public abstract class AbstractInPacketStatusHandler extends PacketHandler {
    public AbstractInPacketStatusHandler(NetworkHandler networkHandler) {
        super(networkHandler);
    }

    public abstract void handle(InPacketStatusStart packet);
    public abstract void handle(InPacketStatusPing packet);
}
