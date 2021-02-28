package net.servzero.network.packet.handler;

import net.servzero.chat.EnumChatType;
import net.servzero.helper.MathHelper;
import net.servzero.logger.Logger;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.*;
import net.servzero.network.packet.in.player.*;
import net.servzero.network.packet.out.OutPacketAnimation;
import net.servzero.network.packet.out.OutPacketChatMessage;
import net.servzero.network.packet.out.entity.OutPacketEntityMetadata;
import net.servzero.server.Server;
import net.servzero.server.entity.EntityMetadata;
import net.servzero.network.serialization.EnumDataSerializers;
import net.servzero.server.game.EnumAnimationAction;
import net.servzero.server.player.Player;
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

        Server.getInstance().getPlayerList().stream().filter(onlinePlayer -> !onlinePlayer.equals(this.player))
                .forEach(onlinePlayer -> onlinePlayer.getNetworkManager().sendPacket(new OutPacketAnimation(this.player.getId(), action)));
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
            } else if (command.startsWith("sneak")) {
                Server.getInstance().getPlayerListExcept(this.player).forEach(onlinePlayer -> onlinePlayer.getNetworkManager().sendPacket(new OutPacketEntityMetadata(this.player.getId(), new EntityMetadata<>(0, EnumDataSerializers.BYTE, (byte) 0x02))));
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

    @Override
    public void handleBlockPlace(InPacketPlayerBlockPlace packet) {
        Position placingPosition = MathHelper.addToPosition(packet.getPosition(), packet.getBlockFace());
        // TODO: Add entity height check
        if (this.player.getWorld().isEntityAtPosition(placingPosition) || this.player.getWorld().isEntityAtPosition(placingPosition.addY(-1))) {
            return;
        }
        this.player.getWorld().getBlockAt(placingPosition).setType(Blocks.STONE);
    }

    @Override
    public void handleBlockDig(InPacketPlayerDig packet) {
        switch (this.player.getGameMode()) {
            case CREATIVE:
                this.player.getWorld().getBlockAt(packet.getPosition()).setType(Blocks.AIR);
                break;
            case SURVIVAL:
                // TODO: Implement survival block digging
                break;
            case SPECTATOR:
            case ADVENTURE:
            case NOT_SET:
            default:
                break;
        }
    }

    @Override
    public void handleEntityAction(InPacketEntityAction packet) {
        switch (packet.getAction()) {
            case START_SNEAKING:
                Server.getInstance().getPlayerListExcept(this.player).forEach(onlinePlayer -> onlinePlayer.getNetworkManager().sendPacket(new OutPacketEntityMetadata(this.player.getId(), new EntityMetadata<>(0, EnumDataSerializers.BYTE, (byte) 0x02))));
                break;
            case STOP_SNEAKING:
                Server.getInstance().getPlayerListExcept(this.player).forEach(onlinePlayer -> onlinePlayer.getNetworkManager().sendPacket(new OutPacketEntityMetadata(this.player.getId(), new EntityMetadata<>(0, EnumDataSerializers.BYTE, (byte) 0x00))));
                break;
            default:
                // TODO: Handle other actions
        }
    }
}