package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.game.EnumHand;

import java.io.IOException;

public class InPacketAnimation implements Packet<AbstractInPacketPlayHandler> {
    private EnumHand hand;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.hand = serializer.readEnum(EnumHand.class);
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handleAnimation(this);
    }

    public EnumHand getHand() {
        return hand;
    }
}
