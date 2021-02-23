package net.servzero.server.player;

import net.servzero.network.NetworkManager;
import net.servzero.server.entity.Entity;

import java.util.UUID;

public class Player extends Entity {
    private final GameProfile profile;
    private ClientSettings settings = new ClientSettings();
    public final NetworkManager networkManager;

    public Player(GameProfile profile, NetworkManager networkManager) {
        this.profile = profile;
        this.networkManager = networkManager;
    }

    public GameProfile getProfile() {
        return profile;
    }

    public ClientSettings getSettings() {
        return settings;
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
}
