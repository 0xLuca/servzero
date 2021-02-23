package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class InPacketPlayer implements Packet<AbstractInPacketHandler> {
    private boolean onGround;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.onGround = serializer.readBoolean();
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketHandler handler) {

    }

    public boolean isOnGround() {
        return onGround;
    }
}
