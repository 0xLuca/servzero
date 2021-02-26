package net.servzero.server.world;

import net.servzero.logger.Logger;
import net.servzero.network.packet.out.entity.OutPacketDestroyEntities;
import net.servzero.network.packet.out.player.OutPacketSpawnPlayer;
import net.servzero.server.Server;
import net.servzero.server.entity.Entity;
import net.servzero.server.game.EnumDimension;
import net.servzero.server.player.Player;
import net.servzero.server.world.block.Blocks;
import net.servzero.server.world.block.Coordinate;
import net.servzero.server.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {
    private final String name;
    private final EnumDimension dimension;
    private final List<Entity> entityList = new ArrayList<>();
    private final List<Chunk> chunkList = new ArrayList<>();

    public World(String name, EnumDimension dimension) {
        this.name = name;
        this.dimension = dimension;
        generate();
    }

    public void generate() {
        //TODO: Implement world generation

        chunkList.add(new Chunk(0, 0));
        chunkList.add(new Chunk(0, -1));
        chunkList.add(new Chunk(-1, 0));
        chunkList.add(new Chunk(-1, -1));

        chunkList.forEach(chunk -> {
            final int y = 0;
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setChunkBlock(Coordinate.get(x, y, z), Blocks.STONE);
                }
            }
        });
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

    public List<Chunk> getChunkList() {
        return chunkList;
    }

    public boolean isChunkLoaded(Coordinate coordinate) {
        for (Chunk chunk : chunkList) {
            if (chunk.isInChunk(coordinate)) {
                return true;
            }
        }
        return false;
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
