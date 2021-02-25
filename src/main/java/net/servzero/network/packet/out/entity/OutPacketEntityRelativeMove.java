package net.servzero.network.packet.out.entity;

import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketEntityRelativeMove extends OutPacketEntity {
    protected final short deltaX;
    protected final short deltaY;
    protected final short deltaZ;
    protected final boolean onGround;

    public OutPacketEntityRelativeMove(int entityId, short deltaX, short deltaY, short deltaZ, boolean onGround) {
        super(entityId);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.onGround = onGround;
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        super.write(serializer);
        serializer.writeShort(deltaX);
        serializer.writeShort(deltaY);
        serializer.writeShort(deltaZ);
        serializer.writeBoolean(onGround);
    }
}
