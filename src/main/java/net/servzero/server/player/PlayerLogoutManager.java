package net.servzero.server.player;

import net.servzero.network.packet.out.OutPacketPlayerListItem;
import net.servzero.server.Server;
import net.servzero.server.game.EnumGameMode;
import net.servzero.server.game.EnumPlayerListAction;

import java.util.ArrayList;

public class PlayerLogoutManager {
    public static void handleLogout(Player player) {
        Server.getInstance().getPlayerList().forEach(onlinePlayer -> onlinePlayer.networkManager.sendPacket(new OutPacketPlayerListItem(
                EnumPlayerListAction.REMOVE_PLAYER,
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
        )));
    }
}
