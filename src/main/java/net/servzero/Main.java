package net.servzero;

import net.servzero.server.Server;

public class Main {
    public static void main(String[] args) {
        System.out.println((1 << 4) | 3);

        Server server = Server.getInstance();

        server.mainThread.start();
    }
}
