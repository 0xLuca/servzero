package net.servzero.network.packet.handler;

import net.servzero.network.NetworkHandler;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.in.InPacketHandshakeSetProtocol;

public abstract class AbstractInPacketHandshakeHandler extends PacketHandler {
    public AbstractInPacketHandshakeHandler(NetworkHandler networkHandler) {
        super(networkHandler);
    }

    abstract void handle(InPacketHandshakeSetProtocol packet);
}
