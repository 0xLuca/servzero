package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class OutPacketLoginDisconnect implements Packet<PacketHandler> {
    private String message;

    public OutPacketLoginDisconnect(String message) {
        this.message = message;
    }

    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeString("{\"text:\" \"" + message + "\"}");
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
