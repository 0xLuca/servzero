package net.servzero.server.player;

import net.servzero.helper.RandomHelper;
import net.servzero.network.packet.handler.InPacketPlayHandler;
import net.servzero.network.packet.out.*;
import net.servzero.network.packet.out.entity.OutPacketEntityHeadLook;
import net.servzero.network.packet.out.entity.OutPacketEntityLook;
import net.servzero.network.packet.out.entity.OutPacketEntityRelativeMoveLook;
import net.servzero.network.packet.out.player.OutPacketPlayerPositionLook;
import net.servzero.server.Server;
import net.servzero.server.game.*;
import net.servzero.server.world.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerLoginManager {
    public static void login(Player player) {
        GameProfile profile = player.getProfile();

        //TODO: Send real information

        player.networkManager.sendPacket(new OutPacketLoginSuccess(profile.getUuid(), profile.getName()));
        player.networkManager.setPacketHandler(new InPacketPlayHandler(player));
        player.networkManager.sendPacket(new OutPacketJoinGame(
                0,
                EnumGameMode.SURVIVAL,
                EnumDimension.OVERWORLD,
                EnumDifficulty.PEACEFUL,
                100,
                EnumLevelType.FLAT,
                false
        ));
        player.networkManager.sendPacket(new OutPacketHeldItemChange(1));
        player.networkManager.sendPacket(new OutPacketDifficulty(EnumDifficulty.PEACEFUL));
        player.networkManager.sendPacket(new OutPacketChunkData());
        player.networkManager.sendPacket(new OutPacketPlayerPositionLook(0, 28, 0, 0, 0, (byte) 0));
        List<Player> onlinePlayerList = Server.getInstance().getPlayerList();
        player.networkManager.sendPacket(new OutPacketPlayerListItem(
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
        player.networkManager.sendPacket(new OutPacketPlayerAbilities(true, false, false, true, 1.0F, 0.0F));

        Server.getInstance().getWorld().spawn(player, new Location(Server.getInstance().getWorld(), 0, 28, 0, 0, 0));
    }
}
