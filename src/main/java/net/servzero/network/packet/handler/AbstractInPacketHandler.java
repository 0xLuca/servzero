package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.in.*;

public abstract class AbstractInPacketHandler extends PacketHandler {
    public AbstractInPacketHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    public abstract void handleClientSettings(InPacketClientSettings packet);
    public abstract void handlePluginMessage(InPacketPluginMessage packet);
    public abstract void handleCloseWindow(InPacketCloseWindow packet);
    public abstract void handleTeleportConfirm(InPacketTeleportConfirm packet);
}
