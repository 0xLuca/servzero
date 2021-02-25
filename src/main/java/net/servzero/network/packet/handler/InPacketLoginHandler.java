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
import java.util.List;
import java.util.UUID;

public class InPacketLoginHandler extends AbstractInPacketLoginHandler {
    public InPacketLoginHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    @Override
    public void handle(InPacketLoginStart packet) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), packet.getUsername());
        Player player = new Player(profile, this.networkManager);

        Server.getInstance().registerPlayer(player);

        //TODO: Write own class & method for this process
        //TODO: Send real information

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
        List<Player> onlinePlayerList = Server.getInstance().getPlayerList();
        this.networkManager.sendPacket(new OutPacketPlayerListItem(
                EnumPlayerListAction.ADD_PLAYER,
                onlinePlayerList.size(),
                new ArrayList<>() {{
                    onlinePlayerList.forEach(onlinePlayer -> add(new OutPacketPlayerListItem.PlayerListItem(
                            onlinePlayer.getUniqueId(),
                            20,
                            EnumGameMode.SURVIVAL,
                            onlinePlayer.getProfile(),
                            onlinePlayer.getName()
                    )));
                }}
        ));
        onlinePlayerList.stream().filter(onlinePlayer -> !onlinePlayer.equals(player)).forEach(onlinePlayer -> {
            onlinePlayer.networkManager.sendPacket(new OutPacketPlayerListItem(
                    EnumPlayerListAction.ADD_PLAYER,
                    1,
                    new ArrayList<>() {{
                        add(new OutPacketPlayerListItem.PlayerListItem(
                                player.getUniqueId(),
                                20,
                                EnumGameMode.SURVIVAL,
                                player.getProfile(),
                                player.getName()
                        ));
                    }}
            ));
        });
        this.networkManager.sendPacket(new OutPacketPlayerAbilities(true, false, false, true, 1.0F, 0.0F));

        //this.networkManager.sendPacket(new OutPacketChunkData());

    }

    @Override
    public void handle(Packet<?> packet) {

    }
}
