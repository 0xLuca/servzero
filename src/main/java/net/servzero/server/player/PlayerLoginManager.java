package net.servzero.server.player;

import net.servzero.network.packet.handler.InPacketPlayHandler;
import net.servzero.network.packet.out.*;
import net.servzero.network.packet.out.player.OutPacketPlayerPositionLook;
import net.servzero.server.Server;
import net.servzero.server.game.*;
import net.servzero.server.world.Location;

import java.util.ArrayList;
import java.util.List;

public class PlayerLoginManager {
    public static void login(Player player) {
        // Create Profile and Spawn Location to Spawn the player at
        GameProfile profile = player.getProfile();
        Location spawnLocation = Location.get(Server.getInstance().getWorld(), 0, 1, 0, 0, 0);

        // Register the player (add to list and set networkmanager's owner)
        Server.getInstance().registerPlayer(player);

        // TODO: Send real information

        // Send the client that he is accepted and set the play-protocol packethandler
        player.getNetworkManager().sendPacket(new OutPacketLoginSuccess(profile.getUuid(), profile.getName()));
        player.getNetworkManager().setPacketHandler(new InPacketPlayHandler(player));

        // Send the client the game info
        player.getNetworkManager().sendPacket(new OutPacketJoinGame(
                player.getId(),
                EnumGameMode.SURVIVAL,
                EnumDimension.OVERWORLD,
                EnumDifficulty.PEACEFUL,
                100,
                EnumLevelType.FLAT,
                false
        ));

        // Send the player some information
        player.getNetworkManager().sendPacket(new OutPacketHeldItemChange(1));
        player.getNetworkManager().sendPacket(new OutPacketDifficulty(EnumDifficulty.PEACEFUL));

        // Send the player the world's chunks
        // TODO: Only send spawn coordinates near chunks
        Server.getInstance().getWorld().getChunkList().forEach(chunk -> player.getNetworkManager().sendPacket(new OutPacketChunkData(chunk)));

        // Send the player its position and rotation (closes downloading terrain screen)
        player.getNetworkManager().sendPacket(new OutPacketPlayerPositionLook(
                spawnLocation.getX(),
                spawnLocation.getY(),
                spawnLocation.getZ(),
                spawnLocation.getYaw(),
                spawnLocation.getPitch(),
                (byte) 0));

        // Send the player the information about all other players
        List<Player> onlinePlayerList = Server.getInstance().getPlayerList();
        player.getNetworkManager().sendPacket(new OutPacketPlayerListItem(
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

        // Send the other players the information about the player
        onlinePlayerList.stream().filter(onlinePlayer -> !onlinePlayer.equals(player)).forEach(onlinePlayer -> {
            onlinePlayer.getNetworkManager().sendPacket(new OutPacketPlayerListItem(
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

        // Send the player his abilities
        player.getNetworkManager().sendPacket(new OutPacketPlayerAbilities(true, false, false, false, 0.1F, 0.0F));

        // Add the player to the world and send him information about the other players and the other players information about him
        Server.getInstance().getWorld().spawn(player, spawnLocation);
    }
}
