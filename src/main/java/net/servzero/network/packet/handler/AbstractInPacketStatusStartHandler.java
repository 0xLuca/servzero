package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.in.InPacketStatusStart;

public abstract class AbstractInPacketStatusStartHandler extends PacketHandler {
    public AbstractInPacketStatusStartHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    public abstract void handle(InPacketStatusStart packet);
}
