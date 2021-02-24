package net.servzero.network.packet.in.player;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.handler.InPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class InPacketPlayerLook extends InPacketPlayer {
    private float yaw;
    private float pitch;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.yaw = serializer.readFloat();
        this.pitch = serializer.readFloat();
        super.read(serializer);
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handlePlayerLook(this);
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
