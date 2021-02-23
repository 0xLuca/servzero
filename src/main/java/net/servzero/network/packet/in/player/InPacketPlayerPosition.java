package net.servzero.network.packet.in.player;

import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class InPacketPlayerPosition extends InPacketPlayer {
    protected double x;
    protected double y; //Feet position, not head
    protected double z;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.x = serializer.readDouble();
        this.y = serializer.readDouble();
        this.z = serializer.readDouble();
        super.read(serializer);
    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handlePlayerPosition(this);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
