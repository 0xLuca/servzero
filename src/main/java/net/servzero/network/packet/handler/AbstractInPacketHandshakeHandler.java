package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.in.InPacketHandshakeSetProtocol;

public abstract class AbstractInPacketHandshakeHandler extends PacketHandler {
    public AbstractInPacketHandshakeHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    abstract void handle(InPacketHandshakeSetProtocol packet);
}
