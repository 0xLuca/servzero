package net.servzero.network.packet.out;

import net.servzero.chat.ChatComponent;
import net.servzero.chat.EnumChatType;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketChatMessage implements Packet<PacketHandler> {
    private String message;
    private EnumChatType type;

    public OutPacketChatMessage(String message, EnumChatType type) {
        this.message = message;
        this.type = type;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeString(ChatComponent.get(this.message));
        serializer.writeEnum(this.type);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
