package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class OutPacketPositionLook implements Packet<PacketHandler> {
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final byte flags;
    private final int teleportId;

    public OutPacketPositionLook(double x, double y, double z, float yaw, float pitch, byte flags, int teleportId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = flags;
        this.teleportId = teleportId;
    }

    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeDouble(x);
        serializer.writeDouble(y);
        serializer.writeDouble(z);
        serializer.writeFloat(yaw);
        serializer.writeFloat(pitch);
        serializer.writeByte(flags);
        serializer.writeVarInt(teleportId);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
