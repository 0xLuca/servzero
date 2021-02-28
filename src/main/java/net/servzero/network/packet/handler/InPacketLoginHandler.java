package net.servzero.network.packet.handler;

import net.servzero.network.NetworkHandler;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.InPacketLoginStart;
import net.servzero.server.game.EnumGameMode;
import net.servzero.server.player.GameProfile;
import net.servzero.server.player.Player;
import net.servzero.server.player.PlayerLoginManager;

import java.util.UUID;

public class InPacketLoginHandler extends AbstractInPacketLoginHandler {
    public InPacketLoginHandler(NetworkHandler networkHandler) {
        super(networkHandler);
    }

    @Override
    public void handle(InPacketLoginStart packet) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), packet.getUsername());
        Player player = new Player(profile, this.networkHandler, EnumGameMode.CREATIVE);

        // TODO: Add encryption and compression

        PlayerLoginManager.login(player);
    }

    @Override
    public void handle(Packet<?> packet) {

    }
}
