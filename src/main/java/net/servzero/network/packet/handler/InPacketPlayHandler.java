package net.servzero.network.packet.handler;

import net.servzero.chat.EnumChatType;
import net.servzero.logger.Logger;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.*;
import net.servzero.network.packet.in.player.InPacketPlayer;
import net.servzero.network.packet.in.player.InPacketPlayerLook;
import net.servzero.network.packet.in.player.InPacketPlayerPosition;
import net.servzero.network.packet.in.player.InPacketPlayerPositionLook;
import net.servzero.network.packet.out.OutPacketChatMessage;
import net.servzero.server.Server;
import net.servzero.server.player.Player;

public class InPacketPlayHandler extends AbstractInPacketPlayHandler {
    private final Player player;

    public InPacketPlayHandler(Player player) {
        super(player.networkManager);
        this.player = player;
    }

    @Override
    public void handle(Packet<?> packet) {

    }

    @Override
    public void handleClientSettings(InPacketClientSettings packet) {
        this.player.setSettings(packet.toClientSettings());
    }

    @Override
    public void handlePluginMessage(InPacketPluginMessage packet) {

    }

    @Override
    public void handleCloseWindow(InPacketCloseWindow packet) {

    }

    @Override
    public void handleTeleportConfirm(InPacketTeleportConfirm packet) {

    }

    @Override
    public void handlePlayer(InPacketPlayer packet) {

    }

    @Override
    public void handlePlayerPosition(InPacketPlayerPosition packet) {
        this.player.updateLocation(() -> {
            this.player.getLocation().setX(packet.getX());
            this.player.getLocation().setY(packet.getY());
            this.player.getLocation().setZ(packet.getZ());
        });
    }

    @Override
    public void handlePlayerLook(InPacketPlayerLook packet) {
        this.player.updateLocation(() -> {
            this.player.getLocation().setYaw(packet.getYaw());
            this.player.getLocation().setPitch(packet.getPitch());
        });
    }

    @Override
    public void handlePlayerPositionLook(InPacketPlayerPositionLook packet) {
        this.player.updateLocation(() -> {
            this.player.getLocation().setX(packet.getX());
            this.player.getLocation().setY(packet.getY());
            this.player.getLocation().setZ(packet.getZ());
            this.player.getLocation().setYaw(packet.getYaw());
            this.player.getLocation().setPitch(packet.getPitch());
        });
    }

    @Override
    public void handleHeldItemChange(InPacketHeldItemChange packet) {

    }

    @Override
    public void handleAnimation(InPacketAnimation packet) {

    }

    @Override
    public void handleKeepAlive(InPacketKeepAlive packet) {

    }

    @Override
    public void handleChatMessage(InPacketChatMessage packet) {
        Logger.info("[Chat] " + this.player.getName()  + ": " + packet.getMessage());
        Server.getInstance().getPlayerList().forEach(player ->
                player.networkManager.sendPacket(new OutPacketChatMessage(
                        this.player.getName() + ": " + packet.getMessage().replace("&", "§"),
                        EnumChatType.CHAT
                )));
    }
}