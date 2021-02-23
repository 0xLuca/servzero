package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class InPacketChatMessage implements Packet<AbstractInPacketPlayHandler> {
    private String message;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.message = serializer.readString(256);
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handleChatMessage(this);
    }

    public String getMessage() {
        return message;
    }
}
