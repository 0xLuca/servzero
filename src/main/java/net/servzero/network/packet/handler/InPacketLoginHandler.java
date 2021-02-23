package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.InPacketLoginStart;
import net.servzero.network.packet.out.OutPacketLoginSuccess;

import java.util.UUID;

public class InPacketLoginHandler extends AbstractInPacketLoginHandler{
    public InPacketLoginHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    @Override
    public void handle(InPacketLoginStart packet) {
        System.out.println("login start: " + packet.getUsername());
        //this.networkManager.sendPacket(new OutPacketLoginSuccess(UUID.randomUUID(), packet.getUsername()));
    }

    @Override
    public void handle(Packet<?> packet) {

    }
}
