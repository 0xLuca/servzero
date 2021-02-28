package net.servzero.server.world;

import net.servzero.helper.Schedulers;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.out.entity.OutPacketDestroyEntities;
import net.servzero.network.packet.out.entity.OutPacketEntityHeadLook;
import net.servzero.network.packet.out.player.OutPacketSpawnPlayer;
import net.servzero.server.Server;
import net.servzero.server.entity.Entity;
import net.servzero.server.game.EnumDimension;
import net.servzero.server.player.Player;
import net.servzero.server.world.block.Block;
import net.servzero.server.world.block.Blocks;
import net.servzero.server.world.block.Position;
import net.servzero.server.world.chunk.Chunk;
import net.servzero.server.world.chunk.ChunkCoordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class World {
    private final String name;
    private final EnumDimension dimension;
    private final List<Entity> entityList = new ArrayList<>();
    private final Map<ChunkCoordinate, Chunk> chunkMap = new HashMap<>();

    public World(String name, EnumDimension dimension) {
        this.name = name;
        this.dimension = dimension;
    }

    public void load() {
        generate();
    }

    private void addChunk(Chunk chunk) {
        chunkMap.put(chunk.getCoordinate(), chunk);
    }

    public void generate() {
        // TODO: Implement world generation

        // Walking chunks
        addChunk(new Chunk(this, 0, 0));
        addChunk(new Chunk(this, 0, -1));
        addChunk(new Chunk(this, -1, 0));
        addChunk(new Chunk(this, -1, -1));

        // Border chunks
        addChunk(new Chunk(this, 1, 1));
        addChunk(new Chunk(this, 0, 1));
        addChunk(new Chunk(this, -1, 1));
        addChunk(new Chunk(this, -2, 1));
        addChunk(new Chunk(this, -2, 0));
        addChunk(new Chunk(this, -2, -1));
        addChunk(new Chunk(this, -2, -2));
        addChunk(new Chunk(this, -1, -2));
        addChunk(new Chunk(this, 0, -2));
        addChunk(new Chunk(this, 1, -2));
        addChunk(new Chunk(this, 1, -1));
        addChunk(new Chunk(this, 1, 0));

        chunkMap.values().forEach(chunk -> {
            if (chunk.getX() < 1 && chunk.getX() >= -1 && chunk.getZ() < 1 && chunk.getZ() >= -1) {
                final int y = 0;
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        chunk.setChunkBlock(Position.get(x, y, z), Blocks.STONE);
                    }
                }
            } else {
                for (int y = 0; y < 4; y++) {
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            chunk.setChunkBlock(Position.get(x, y, z), Blocks.BARRIER);
                        }
                    }
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
        return new ArrayList<>(chunkMap.values());
    }

    public Chunk getSpawnChunk(Location location) {
        return getChunkAt(location.asPosition());
    }

    public List<Player> getPlayerList() {
        return Server.getInstance().getPlayerList().stream().filter(player -> player.getWorld().equals(this)).collect(Collectors.toList());
    }

    public boolean isEntityAtPosition(Position position) {
        Location location = Location.fromPosition(this, position);
        return entityList.stream().anyMatch(entity -> entity.getLocation().equals(location));
    }

    public void sendToAllInWorld(Packet<?> packet) {
        if (getPlayerList().size() > 0) {
            getPlayerList().forEach(player -> player.getNetworkManager().sendPacket(packet));
        }
    }

    public boolean isChunkLoaded(Position position) {
        for (Chunk chunk : chunkMap.values()) {
            if (chunk.isInChunk(position)) {
                return true;
            }
        }
        return false;
    }

    public Block getBlockAt(Position position) {
        final Chunk chunk = getChunkAt(position);
        return chunk.getBlockAt(position);
    }

    public Chunk getChunkAt(Position position) {
        int chunkX = (int) Math.floor(position.getX() / 16.0D);
        int chunkZ = (int) Math.floor(position.getZ() / 16.0D);
        return chunkMap.getOrDefault(ChunkCoordinate.get(chunkX, chunkZ), null);
    }

    public void leave(Player player) {
        this.entityList.remove(player);

        Server.getInstance().getPlayerList().forEach(onlinePlayer -> {
            onlinePlayer.getNetworkManager().sendPacket(new OutPacketDestroyEntities(player.getId()));
        });
    }

    public void spawn(Entity entity, Location location) {
        entity.setInitialLocation(location);
        entityList.add(entity);

        if (entity instanceof Player) {
            final Player newPlayer = (Player) entity;
            Server.getInstance().getPlayerList().stream().filter(player -> player.getLocation().getWorld().equals(this) && !entity.equals(player)).forEach(onlinePlayer -> {
                // Spawn new player to other player
                onlinePlayer.getNetworkManager().sendPacket(new OutPacketSpawnPlayer(
                        entity.getId(),
                        newPlayer.getUniqueId(),
                        location.getX(),
                        location.getY(),
                        location.getZ(),
                        location.getYaw(),
                        location.getPitch()
                ));
            });

            Schedulers.runLaterAsync(() -> {
                Server.getInstance().getPlayerList().stream().filter(player -> player.getLocation().getWorld().equals(this) && !entity.equals(player)).forEach(onlinePlayer -> {
                    // Spawn other player to new player
                    newPlayer.getNetworkManager().sendPacket(new OutPacketSpawnPlayer(
                            onlinePlayer.getId(),
                            onlinePlayer.getUniqueId(),
                            onlinePlayer.getLocation().getX(),
                            onlinePlayer.getLocation().getY(),
                            onlinePlayer.getLocation().getZ(),
                            onlinePlayer.getLocation().getYaw(),
                            onlinePlayer.getLocation().getPitch()
                    ));

                    // Send other player's head rotation to new player
                    newPlayer.getNetworkManager().sendPacket(new OutPacketEntityHeadLook(
                            onlinePlayer.getId(),
                            onlinePlayer.getLocation().getYaw()
                    ));
                });
            }, 50L);
        }
    }

}
