package net.servzero.network.packet.out.entity;

import net.servzero.helper.AngleHelper;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketEntityLook extends OutPacketEntity {
    private byte yawAngle;
    private byte pitchAngle;
    private boolean onGround;

    public OutPacketEntityLook(int entityId, float yaw, float pitch, boolean onGround) {
        super(entityId);
        this.yawAngle = AngleHelper.getAngleFromRotation(yaw);
        this.pitchAngle = AngleHelper.getAngleFromRotation(pitch);
        this.onGround = onGround;
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        super.write(serializer);
        serializer.writeByte(yawAngle);
        serializer.writeByte(pitchAngle);
        serializer.writeBoolean(onGround);
    }
}
