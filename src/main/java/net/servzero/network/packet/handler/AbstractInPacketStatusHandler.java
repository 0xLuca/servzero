package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.in.InPacketStatusPing;
import net.servzero.network.packet.in.InPacketStatusStart;

public abstract class AbstractInPacketStatusHandler extends PacketHandler {
    public AbstractInPacketStatusHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    public abstract void handle(InPacketStatusStart packet);
    public abstract void handle(InPacketStatusPing packet);
}
