package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.InPacketLoginStart;
import net.servzero.network.packet.out.*;
import net.servzero.server.Server;
import net.servzero.server.game.*;
import net.servzero.server.player.GameProfile;
import net.servzero.server.player.Player;
import net.servzero.server.player.PlayerLoginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InPacketLoginHandler extends AbstractInPacketLoginHandler {
    public InPacketLoginHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    @Override
    public void handle(InPacketLoginStart packet) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), packet.getUsername());
        Player player = new Player(profile, this.networkManager);

        // TODO: Add encryption and compression

        PlayerLoginManager.login(player);
    }

    @Override
    public void handle(Packet<?> packet) {

    }
}
