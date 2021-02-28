package net.servzero.network.packet;

import net.servzero.network.NetworkHandler;

public abstract class PacketHandler {
    protected NetworkHandler networkHandler;

    public PacketHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    public abstract void handle(Packet<?> packet);
}
