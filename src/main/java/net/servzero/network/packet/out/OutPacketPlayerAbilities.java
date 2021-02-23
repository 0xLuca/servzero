package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketPlayerAbilities implements Packet<PacketHandler> {
    private final byte flags;
    private final float flySpeed;
    private final float fovModifier;

    public OutPacketPlayerAbilities(boolean invulnerable, boolean flying, boolean allowFlying, boolean instantBreak, float flySpeed, float fovModifier) {
        byte flags = 0;
        flags += invulnerable ? 0x01 : 0;
        flags += flying ? 0x02 : 0;
        flags += allowFlying ? 0x04 : 0;
        flags += instantBreak ? 0x08 : 0;
        this.flags = flags;
        this.flySpeed = flySpeed;
        this.fovModifier = fovModifier;
    }

    public OutPacketPlayerAbilities(byte flags, float flySpeed, float fovModifier) {
        this.flags = flags;
        this.flySpeed = flySpeed;
        this.fovModifier = fovModifier;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeByte(flags);
        serializer.writeFloat(flySpeed);
        serializer.writeFloat(fovModifier);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
