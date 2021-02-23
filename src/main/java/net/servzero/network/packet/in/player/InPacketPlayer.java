package net.servzero.network.packet.in.player;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class InPacketPlayer implements Packet<AbstractInPacketPlayHandler> {
    protected boolean onGround;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.onGround = serializer.readBoolean();
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handlePlayer(this);
    }

    public boolean isOnGround() {
        return onGround;
    }
}
