package net.servzero.network.packet.out.player;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.game.EnumSound;
import net.servzero.server.game.EnumSoundCategory;
import net.servzero.server.world.Location;

import java.io.IOException;

public class OutPacketSoundEffect implements Packet<PacketHandler> {
    private final EnumSound sound;
    private final EnumSoundCategory category;
    private final Location location;
    private final float volume;
    private final float pitch;

    public OutPacketSoundEffect(EnumSound sound, EnumSoundCategory category, Location location, float volume, float pitch) {
        this.sound = sound;
        this.category = category;
        this.location = location;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeVarInt(this.sound.getId());
        serializer.writeEnum(this.category);
        serializer.writeInt((int) (this.location.getX() ));
        serializer.writeInt((int) (this.location.getY() ));
        serializer.writeInt((int) (this.location.getZ() ));
        serializer.writeFloat(this.volume);
        serializer.writeFloat(this.pitch);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
