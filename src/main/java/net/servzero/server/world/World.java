package net.servzero.server.world;

import net.servzero.logger.Logger;
import net.servzero.network.packet.out.entity.OutPacketDestroyEntities;
import net.servzero.network.packet.out.player.OutPacketSpawnPlayer;
import net.servzero.server.Server;
import net.servzero.server.entity.Entity;
import net.servzero.server.game.EnumDimension;
import net.servzero.server.player.Player;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final String name;
    private final EnumDimension dimension;
    private final List<Entity> entityList = new ArrayList<>();

    public World(String name, EnumDimension dimension) {
        this.name = name;
        this.dimension = dimension;
    }

    public String getName() {
        return name;
    }

    public EnumDimension getDimension() {
        return dimension;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void leave(Player player) {
        this.entityList.remove(player);

        Server.getInstance().getPlayerList().forEach(onlinePlayer -> {
            onlinePlayer.networkManager.sendPacket(new OutPacketDestroyEntities(player.getId()));
        });
    }

    public void spawn(Entity entity, Location location) {
        entity.teleport(location);
        entityList.add(entity);

        if (entity instanceof Player) {
            final Player entityPlayer = (Player) entity;
            Server.getInstance().getPlayerList().stream().filter(player -> player.getLocation().getWorld().equals(this) && !entity.equals(player)).forEach(player -> {
                // Spawn new player to other player
                player.networkManager.sendPacket(new OutPacketSpawnPlayer(
                        entity.getId(),
                        entityPlayer.getUniqueId(),
                        location.getX(),
                        location.getY(),
                        location.getZ(),
                        location.getYaw(),
                        location.getPitch()
                ));

                // Spawn other player to new player
                Logger.info("Sending " + entityPlayer.getName() + " the player " + player.getName() + " at " + player.getLocation());
                entityPlayer.networkManager.sendPacket(new OutPacketSpawnPlayer(
                        player.getId(),
                        player.getUniqueId(),
                        player.getLocation().getX(),
                        player.getLocation().getY(),
                        player.getLocation().getZ(),
                        player.getLocation().getYaw(),
                        player.getLocation().getPitch()
                ));
            });
        }
    }
}
