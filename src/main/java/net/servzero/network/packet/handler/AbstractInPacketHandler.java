package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.in.*;
import net.servzero.network.packet.in.player.InPacketPlayer;
import net.servzero.network.packet.in.player.InPacketPlayerPosition;
import net.servzero.network.packet.in.player.InPacketPlayerPositionLook;

public abstract class AbstractInPacketHandler extends PacketHandler {
    public AbstractInPacketHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    public abstract void handleClientSettings(InPacketClientSettings packet);
    public abstract void handlePluginMessage(InPacketPluginMessage packet);
    public abstract void handleCloseWindow(InPacketCloseWindow packet);
    public abstract void handleTeleportConfirm(InPacketTeleportConfirm packet);

    public abstract void handlePlayer(InPacketPlayer packet);
    public abstract void handlePlayerPosition(InPacketPlayerPosition packet);
    public abstract void handlePlayerPositionLook(InPacketPlayerPositionLook packet);

    public abstract void handleHeldItemChange(InPacketHeldItemChange packet);
    public abstract void handleAnimation(InPacketAnimation inPacketAnimation);
    public abstract void handleKeepAlive(InPacketKeepAlive inPacketKeepAlive);
}
