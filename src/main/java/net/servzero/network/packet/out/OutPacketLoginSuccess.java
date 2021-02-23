package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.util.UUID;

public class OutPacketLoginSuccess implements Packet<PacketHandler> {
    private UUID uuid;
    private String username;

    public OutPacketLoginSuccess(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeString(uuid == null ? "" : uuid.toString());
        serializer.writeString(username);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
