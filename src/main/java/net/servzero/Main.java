package net.servzero;

import net.servzero.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();

        server.mainThread.start();
    }
}
