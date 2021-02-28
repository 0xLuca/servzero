package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.in.*;
import net.servzero.network.packet.in.player.*;

public abstract class AbstractInPacketPlayHandler extends PacketHandler {
    public AbstractInPacketPlayHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    public abstract void handleClientSettings(InPacketClientSettings packet);
    public abstract void handlePluginMessage(InPacketPluginMessage packet);
    public abstract void handleCloseWindow(InPacketCloseWindow packet);
    public abstract void handleTeleportConfirm(InPacketTeleportConfirm packet);

    public abstract void handlePlayer(InPacketPlayer packet);
    public abstract void handlePlayerPosition(InPacketPlayerPosition packet);
    public abstract void handlePlayerLook(InPacketPlayerLook packet);
    public abstract void handlePlayerPositionLook(InPacketPlayerPositionLook packet);

    public abstract void handleHeldItemChange(InPacketHeldItemChange packet);
    public abstract void handleAnimation(InPacketAnimation packet);
    public abstract void handleKeepAlive(InPacketKeepAlive packet);
    public abstract void handleChatMessage(InPacketChatMessage packet);

    public abstract void handleBlockPlace(InPacketPlayerBlockPlace packet);
    public abstract void handleBlockDig(InPacketPlayerDig packet);
}
