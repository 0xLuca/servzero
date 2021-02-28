package net.servzero.network.packet.in.player;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.game.EnumBlockFace;
import net.servzero.server.game.EnumDigStatus;
import net.servzero.server.world.block.Position;

import java.io.IOException;

public class InPacketPlayerDig implements Packet<AbstractInPacketPlayHandler> {
    private EnumDigStatus status;
    private Position position;
    private EnumBlockFace blockFace;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.status = serializer.readEnum(EnumDigStatus.class);
        this.position = Position.fromSerializedPosition(serializer.readLong());
        this.blockFace = serializer.readEnum(EnumBlockFace.class);
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handleBlockDig(this);
    }

    public EnumDigStatus getStatus() {
        return status;
    }

    public Position getPosition() {
        return position;
    }

    public EnumBlockFace getBlockFace() {
        return blockFace;
    }
}
