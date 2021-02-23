package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.InPacketLoginStart;
import net.servzero.network.packet.out.*;
import net.servzero.server.Server;
import net.servzero.server.game.*;
import net.servzero.server.player.GameProfile;
import net.servzero.server.player.Player;

import java.util.ArrayList;
import java.util.UUID;

public class InPacketLoginHandler extends AbstractInPacketLoginHandler{
    public InPacketLoginHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    @Override
    public void handle(InPacketLoginStart packet) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), packet.getUsername());
        Player player = new Player(profile, this.networkManager);

        Server.getInstance().registerPlayer(player);

        this.networkManager.sendPacket(new OutPacketLoginSuccess(profile.getUuid(), profile.getName()));
        this.networkManager.setPacketHandler(new InPacketPlayHandler(player));
        this.networkManager.sendPacket(new OutPacketJoinGame(
                0,
                EnumGameMode.SURVIVAL,
                EnumDimension.OVERWORLD,
                EnumDifficulty.PEACEFUL,
                100,
                EnumLevelType.FLAT,
                false
        ));
        this.networkManager.sendPacket(new OutPacketHeldItemChange(1));
        this.networkManager.sendPacket(new OutPacketDifficulty(EnumDifficulty.PEACEFUL));
        this.networkManager.sendPacket(new OutPacketPositionLook(0, 10, 0, 0, 0, (byte) 0, 0));
        this.networkManager.sendPacket(new OutPacketPlayerListItem(
                EnumPlayerListAction.ADD_PLAYER,
                1,
                new ArrayList<>() {{
                    add(new OutPacketPlayerListItem.PlayerListItem(
                            player.getProfile().getUuid(),
                            20,
                            EnumGameMode.SURVIVAL,
                            player.getProfile(),
                            player.getProfile().getName()
                    ));
                }}
        ));
        this.networkManager.sendPacket(new OutPacketPlayerAbilities(true, true, true, true, 1.0F, 0.0F));
    }

    @Override
    public void handle(Packet<?> packet) {

    }
}
