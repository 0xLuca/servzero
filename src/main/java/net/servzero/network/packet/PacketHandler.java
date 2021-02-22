package net.servzero.network.packet;

import net.servzero.network.NetworkManager;

public abstract class PacketHandler {
    protected NetworkManager networkManager;

    public PacketHandler(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public abstract void handle(Packet<?> packet);
}
