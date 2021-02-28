package net.servzero.network.packet.in.player;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.game.EnumBlockFace;
import net.servzero.server.game.EnumHand;
import net.servzero.server.world.block.Position;

import java.io.IOException;

public class InPacketPlayerBlockPlace implements Packet<AbstractInPacketPlayHandler> {
    private Position position;
    private EnumBlockFace blockFace;
    private EnumHand hand;
    private float cursorX;
    private float cursorY;
    private float cursorZ;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.position = Position.fromSerializedPosition(serializer.readLong());
        this.blockFace = serializer.readEnum(EnumBlockFace.class);
        this.hand = serializer.readEnum(EnumHand.class);
        this.cursorX = serializer.readFloat();
        this.cursorY = serializer.readFloat();
        this.cursorZ = serializer.readFloat();
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handleBlockPlace(this);
    }

    public Position getPosition() {
        return position;
    }

    public EnumBlockFace getBlockFace() {
        return blockFace;
    }

    public EnumHand getHand() {
        return hand;
    }

    public float getCursorX() {
        return cursorX;
    }

    public float getCursorY() {
        return cursorY;
    }

    public float getCursorZ() {
        return cursorZ;
    }
}
