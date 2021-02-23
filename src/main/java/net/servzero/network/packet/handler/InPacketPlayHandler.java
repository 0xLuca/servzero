package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.InPacketClientSettings;
import net.servzero.server.player.Player;

public class InPacketPlayHandler extends AbstractInPacketHandler {
    private final Player player;

    public InPacketPlayHandler(Player player) {
        super(player.networkManager);
        this.player = player;
    }

    @Override
    public void handle(Packet<?> packet) {

    }

    @Override
    public void handleClientSettings(InPacketClientSettings packet) {
        this.player.setSettings(packet.toClientSettings());
    }
}
