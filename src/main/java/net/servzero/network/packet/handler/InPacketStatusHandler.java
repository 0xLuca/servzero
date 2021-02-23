package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.InPacketStatusPing;
import net.servzero.network.packet.in.InPacketStatusStart;
import net.servzero.network.packet.out.OutPacketStatusPong;
import net.servzero.network.packet.out.OutPacketStatusResponse;
import net.servzero.network.ping.PingResponse;

import java.util.ArrayList;
import java.util.UUID;

public class InPacketStatusHandler extends AbstractInPacketStatusHandler {
    private int clientVersion = -1;

    public InPacketStatusHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    @Override
    public void handle(InPacketStatusStart packet) {
        PingResponse response = new PingResponse(
                new PingResponse.VersionInfo("1.0.0", 340),
                new PingResponse.PlayerInfo(100, 47, new ArrayList<>() {{
                    add(new PingResponse.PlayerInfoItem("Name1", UUID.randomUUID()));
                    add(new PingResponse.PlayerInfoItem("Name2", UUID.randomUUID()));
                    add(new PingResponse.PlayerInfoItem("Name3", UUID.randomUUID()));
                }}),
                new PingResponse.Description("Testserver"),
                 ""
        );
        networkManager.sendPacket(new OutPacketStatusResponse(response));
    }

    @Override
    public void handle(InPacketStatusPing packet) {
        this.networkManager.sendPacket(new OutPacketStatusPong(packet.getSentTime()));
        this.networkManager.close();
    }

    @Override
    public void handle(Packet<?> packet) {

    }

    public void setClientVersion(int clientVersion) {
        this.clientVersion = clientVersion;
    }
}
