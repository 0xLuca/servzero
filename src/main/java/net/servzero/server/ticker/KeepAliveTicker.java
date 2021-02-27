package net.servzero.server.ticker;

import net.servzero.network.packet.out.OutPacketKeepAlive;
import net.servzero.server.Server;

public class KeepAliveTicker implements Runnable {
    private final Server server;

    public KeepAliveTicker(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            server.getPlayerList().forEach(player -> {
                player.getNetworkManager().sendPacket(new OutPacketKeepAlive());
            });
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
