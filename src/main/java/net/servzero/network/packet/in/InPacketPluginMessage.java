package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class InPacketPluginMessage implements Packet<AbstractInPacketHandler> {
    private String channel;
    private PacketDataSerializer data;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.channel = serializer.readString(20);
        int length = serializer.readableBytes();
        if (length >= 0 && length <= 32767) {
            this.data = new PacketDataSerializer(serializer.readBytes(length));
        } else {
            throw new IOException("Payload length too large!");
        }
    }

    @Override
    public void write(PacketDataSerializer serializer) {

    }

    @Override
    public void handle(AbstractInPacketHandler handler) {

    }

    public String getChannel() {
        return channel;
    }

    public PacketDataSerializer getData() {
        return data;
    }
}
