package net.servzero.network.packet.handler;

import net.servzero.network.NetworkHandler;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.in.InPacketLoginStart;

public abstract class AbstractInPacketLoginHandler extends PacketHandler {
    public AbstractInPacketLoginHandler(NetworkHandler networkHandler) {
        super(networkHandler);
    }

    public abstract void handle(InPacketLoginStart packet);
}
