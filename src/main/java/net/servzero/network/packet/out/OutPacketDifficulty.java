package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.game.EnumDifficulty;

public class OutPacketDifficulty implements Packet<PacketHandler> {
    private final EnumDifficulty difficulty;

    public OutPacketDifficulty(EnumDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeByte(difficulty.getId());
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
