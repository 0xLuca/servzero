package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.InPacketLoginHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class InPacketLoginStart implements Packet<InPacketLoginHandler> {
    private String username;

    @Override
    public void read(PacketDataSerializer serializer) {
        this.username = serializer.readString(16);
    }

    @Override
    public void write(PacketDataSerializer serializer) {

    }

    @Override
    public void handle(InPacketLoginHandler handler) {
        handler.handle(this);
    }

    public String getUsername() {
        return username;
    }
}
