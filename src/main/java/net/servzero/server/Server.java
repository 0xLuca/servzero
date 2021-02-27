package net.servzero.server;

import net.servzero.logger.Logger;
import net.servzero.network.packet.out.OutPacketPlayerListItem;
import net.servzero.server.game.EnumDimension;
import net.servzero.server.game.EnumGameMode;
import net.servzero.server.game.EnumPlayerListAction;
import net.servzero.server.player.Player;
import net.servzero.server.player.PlayerLogoutManager;
import net.servzero.server.ticker.KeepAliveTicker;
import net.servzero.server.world.World;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server implements Runnable {
    private static Server INSTANCE = null;

    public static final List<Integer> IGNORED_PACKETS = new ArrayList<>() {{
        add(0x4D); // Advancements
        add(0x07); // Window click
    }};

    public final Thread mainThread;
    public Thread keepAliveThread;
    private final Connector connector;
    private boolean running;
    private final List<Player> playerList = new ArrayList<>();
    private final World primaryWorld;

    private Server() {
        this.connector = new Connector();
        this.primaryWorld = new World("main world", EnumDimension.OVERWORLD);

        this.mainThread = new Thread(this, "Server thread");
        registerWorkers();
    }

    private void registerWorkers() {
        this.keepAliveThread = new Thread(new KeepAliveTicker(this), "KeepAlive thread");
        this.keepAliveThread.start();
    }

    public static Server getInstance() {
        return INSTANCE == null ? (INSTANCE = new Server()) : INSTANCE;
    }

    public boolean start() {
        Logger.info("Starting Server...");

        try {
            connector.initialize(null, 25565);
        } catch (IOException exception) {
            Logger.error("* FAILED TO BIND TO PORT *");
            Logger.error("Exception: " + exception.toString());
            Logger.error("Perhaps a server is already running on that port?");
            return false;
        }

        this.running = true;
        Logger.info("Done.");
        return true;
    }

    public void run() {
        if (this.start()) {
            while (this.running) {
                //TODO: Run server tick
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public List<Player> getPlayerList() {
        return this.playerList;
    }

    public List<Player> getPlayerListExcept(Player... players) {
        final List<Player> exceptions = Arrays.asList(players);
        final List<Player> removed = new ArrayList<>(this.playerList);
        removed.removeAll(exceptions);
        return removed;
    }

    public void registerPlayer(Player player) {
        this.playerList.add(player);
        player.networkManager.setOwner(player);
        Logger.info("Player " + player.getName() + " joined.");
    }

    public void unregisterPlayer(Player player) {
        this.playerList.remove(player);
        this.primaryWorld.leave(player);
        Logger.info("Player " + player.getName() + " left.");
    }

    public World getWorld() {
        return this.primaryWorld;
    }
}
