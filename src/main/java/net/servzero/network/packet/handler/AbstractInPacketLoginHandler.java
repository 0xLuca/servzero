package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.in.InPacketLoginStart;

public abstract class AbstractInPacketLoginHandler extends PacketHandler {
    public AbstractInPacketLoginHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    public abstract void handle(InPacketLoginStart packet);
}
