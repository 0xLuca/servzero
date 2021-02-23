package net.servzero.network.packet.out;

import com.google.gson.Gson;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.network.ping.PingResponse;

public class OutPacketStatusResponse implements Packet<PacketHandler> {
    private static final Gson gson = new Gson();
    private PingResponse response;

    public OutPacketStatusResponse(PingResponse response) {
        this.response = response;
    }

    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeString(gson.toJson(response));
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
