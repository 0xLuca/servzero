package net.servzero.network.packet.handler;

import net.servzero.chat.EnumChatType;
import net.servzero.logger.Logger;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.*;
import net.servzero.network.packet.in.player.InPacketPlayer;
import net.servzero.network.packet.in.player.InPacketPlayerLook;
import net.servzero.network.packet.in.player.InPacketPlayerPosition;
import net.servzero.network.packet.in.player.InPacketPlayerPositionLook;
import net.servzero.network.packet.out.OutPacketAnimation;
import net.servzero.network.packet.out.OutPacketChatMessage;
import net.servzero.server.Server;
import net.servzero.server.game.EnumAnimationAction;
import net.servzero.server.player.Player;
import net.servzero.server.world.block.BlockState;
import net.servzero.server.world.block.Blocks;
import net.servzero.server.world.block.Position;

public class InPacketPlayHandler extends AbstractInPacketPlayHandler {
    private final Player player;

    public InPacketPlayHandler(Player player) {
        super(player.getNetworkManager());
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
        this.player.setPosition(packet.getX(), packet.getY(), packet.getZ());
    }

    @Override
    public void handlePlayerLook(InPacketPlayerLook packet) {
        this.player.setRotation(packet.getYaw(), packet.getPitch());
    }

    @Override
    public void handlePlayerPositionLook(InPacketPlayerPositionLook packet) {
        this.player.setPositionAndRotation(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
    }

    @Override
    public void handleHeldItemChange(InPacketHeldItemChange packet) {

    }

    @Override
    public void handleAnimation(InPacketAnimation packet) {
        final int action;
        switch (packet.getHand()) {
            case RIGHT:
                action = EnumAnimationAction.SWING_MAIN_HAND.getId();
                break;
            case LEFT:
                action = EnumAnimationAction.SWING_OFF_HAND.getId();
                break;
            default:
                action = -1;
        }

        //this.player.getWorld().getBlockAt(this.player.getLocation().asPosition()).setType(Blocks.STONE);

        Server.getInstance().getPlayerList().stream().filter(onlinePlayer -> !onlinePlayer.equals(this.player)).forEach(onlinePlayer -> {
            onlinePlayer.getNetworkManager().sendPacket(new OutPacketAnimation(this.player.getId(), action));
        });
    }

    @Override
    public void handleKeepAlive(InPacketKeepAlive packet) {

    }

    @Override
    public void handleChatMessage(InPacketChatMessage packet) {
        String message = packet.getMessage();
        if (message.startsWith("/")) {
            String command = message.substring(1);
            if (command.startsWith("block")) {
                this.player.getWorld().getBlockAt(Position.get(0, 1, 0)).setType(Blocks.STONE);
                this.player.sendMessage(this.player.getWorld().getChunkAt(this.player.getLocation().asPosition()).getX() + "" + this.player.getWorld().getChunkAt(this.player.getLocation().asPosition()).getZ());
            }
            return;
        }
        Logger.info("[Chat] " + this.player.getName()  + ": " + packet.getMessage());
        Server.getInstance().getPlayerList().forEach(player ->
                player.getNetworkManager().sendPacket(new OutPacketChatMessage(
                        this.player.getName() + ": " + packet.getMessage().replace("&", "§"),
                        EnumChatType.CHAT
                )));
    }
}