package net.servzero.network.packet.out;

import net.servzero.chat.ChatComponent;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class OutPacketDisconnect implements Packet<PacketHandler> {
    private String message;

    public OutPacketDisconnect(String message) {
        this.message = message;
    }

    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeString(ChatComponent.get(message));
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
