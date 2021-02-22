package net.servzero.network.packet.handler;

import com.google.gson.Gson;
import net.servzero.network.NetworkManager;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.InPacketStatusStart;
import net.servzero.network.ping.PingResponse;

import java.util.ArrayList;
import java.util.UUID;

public class InPacketStatusStartHandler extends AbstractInPacketStatusStartHandler {
    private int clientVersion = -1;

    public InPacketStatusStartHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    @Override
    public void handle(InPacketStatusStart packet) {
        System.out.println("Handling Start!");
        System.out.println("JSON IS: ");
        PingResponse response = new PingResponse(
                new PingResponse.VersionInfo("1.0.0", 300),
                new PingResponse.PlayerInfo(100, 0, new ArrayList<>() {{
                    add(new PingResponse.PlayerInfoItem("Name1", UUID.randomUUID()));
                    add(new PingResponse.PlayerInfoItem("Name2", UUID.randomUUID()));
                    add(new PingResponse.PlayerInfoItem("Name3", UUID.randomUUID()));
                }}),
                new PingResponse.Description("Testserver"),
                 ""
        );
        System.out.println(new Gson().toJson(response));
    }

    @Override
    public void handle(Packet<?> packet) {

    }

    public void setClientVersion(int clientVersion) {
        this.clientVersion = clientVersion;
    }
}
