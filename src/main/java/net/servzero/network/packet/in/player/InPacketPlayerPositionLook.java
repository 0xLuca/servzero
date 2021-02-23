package net.servzero.network.packet.in.player;

import net.servzero.network.packet.handler.AbstractInPacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class InPacketPlayerPositionLook extends InPacketPlayerPosition {
    private float yaw;
    private float pitch;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.x = serializer.readDouble();
        this.y = serializer.readDouble();
        this.z = serializer.readDouble();
        this.yaw = serializer.readFloat();
        this.pitch = serializer.readFloat();
        this.onGround = serializer.readBoolean();
    }

    @Override
    public void handle(AbstractInPacketHandler handler) {
        handler.handlePlayerPositionLook(this);
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
