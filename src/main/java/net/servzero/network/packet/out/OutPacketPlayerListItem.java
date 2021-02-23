package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.game.EnumGameMode;
import net.servzero.server.game.EnumPlayerListAction;
import net.servzero.server.player.GameProfile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class OutPacketPlayerListItem implements Packet<PacketHandler> {
    private final EnumPlayerListAction action;
    private final int playerCount;
    private final List<PlayerListItem> items;

    public OutPacketPlayerListItem(EnumPlayerListAction action, int playerCount, List<PlayerListItem> items) {
        this.action = action;
        this.playerCount = playerCount;
        this.items = items;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeEnum(action);
        serializer.writeVarInt(playerCount);
        items.forEach(playerListItem -> {
            serializer.writeUuid(playerListItem.getUuid());
            switch (action) {
                case ADD_PLAYER:
                    serializer.writeString(playerListItem.getProfile().getName());
                    serializer.writeVarInt(0); //TODO: Add properties
                    serializer.writeEnum(playerListItem.getGameMode());
                    serializer.writeVarInt(playerListItem.getPing());
                    serializer.writeBoolean(false); //TODO: Add display names
                    break;
                case UPDATE_GAMEMODE:
                    serializer.writeEnum(playerListItem.getGameMode());
                    break;
                case UPDATE_LATENCY:
                    serializer.writeVarInt(playerListItem.getPing());
                    break;
                case UPDATE_DISPLAY_NAME:
                    serializer.writeBoolean(false); //TODO: Add display names
                    break;
                case REMOVE_PLAYER:
                default:
            }
        });
    }

    @Override
    public void handle(PacketHandler handler) {

    }

    public static class PlayerListItem {
        private final UUID uuid;
        private final int ping;
        private final EnumGameMode gameMode;
        private final GameProfile profile;
        private final String displayName;

        public PlayerListItem(UUID uuid, int ping, EnumGameMode gameMode, GameProfile profile, String displayName) {
            this.uuid = uuid;
            this.ping = ping;
            this.gameMode = gameMode;
            this.profile = profile;
            this.displayName = displayName;
        }

        public UUID getUuid() {
            return uuid;
        }

        public int getPing() {
            return ping;
        }

        public EnumGameMode getGameMode() {
            return gameMode;
        }

        public GameProfile getProfile() {
            return profile;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
