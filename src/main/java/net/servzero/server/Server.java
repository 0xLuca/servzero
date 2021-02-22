package net.servzero.server;

import net.servzero.logger.Logger;

import java.io.IOException;
import java.net.InetAddress;

public class Server implements Runnable {
    public final Thread mainThread;
    private Connector connector;
    private boolean running;

    public Server() {
        this.connector = new Connector();

        this.mainThread = new Thread(this, "Server thread");
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
}
