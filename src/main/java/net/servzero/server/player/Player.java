package net.servzero.server.player;

import net.servzero.chat.EnumChatType;
import net.servzero.network.NetworkManager;
import net.servzero.network.packet.out.OutPacketChatMessage;
import net.servzero.server.entity.Entity;
import net.servzero.server.game.EnumGameMode;

import java.util.UUID;

public class Player extends Entity {
    private final GameProfile profile;
    private ClientSettings settings = new ClientSettings();
    private final NetworkManager networkManager;
    private EnumGameMode gameMode;

    public Player(GameProfile profile, NetworkManager networkManager, EnumGameMode gameMode) {
        super();
        this.profile = profile;
        this.networkManager = networkManager;
        this.gameMode = gameMode;
    }

    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    public GameProfile getProfile() {
        return this.profile;
    }

    public ClientSettings getSettings() {
        return this.settings;
    }

    public void setSettings(ClientSettings settings) {
        this.settings = settings;
    }

    public String getName() {
        return this.profile.getName();
    }

    public UUID getUniqueId() {
        return this.profile.getUuid();
    }

    public EnumGameMode getGameMode() {
        return gameMode;
    }

    public void sendMessage(String message) {
        this.networkManager.sendPacket(new OutPacketChatMessage(message, EnumChatType.SYSTEM));
    }
}
