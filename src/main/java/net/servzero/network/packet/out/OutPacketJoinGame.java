package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.game.EnumDifficulty;
import net.servzero.server.game.EnumDimension;
import net.servzero.server.game.EnumGameMode;
import net.servzero.server.game.EnumLevelType;

public class OutPacketJoinGame implements Packet<PacketHandler> {
    private int entityId;
    private EnumGameMode gameMode;
    private EnumDimension dimension;
    private EnumDifficulty difficulty;
    private int maxPlayers;
    private EnumLevelType levelType;
    private boolean reducedDebugInfo;

    public OutPacketJoinGame(int entityId, EnumGameMode gameMode, EnumDimension dimension, EnumDifficulty difficulty, int maxPlayers, EnumLevelType levelType, boolean reducedDebugInfo) {
        this.entityId = entityId;
        this.gameMode = gameMode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.levelType = levelType;
        this.reducedDebugInfo = reducedDebugInfo;
    }

    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeInt(this.entityId);
        serializer.writeByte(this.gameMode.getId());
        serializer.writeInt(this.dimension.getId());
        serializer.writeByte(this.difficulty.getId());
        serializer.writeByte(this.maxPlayers);
        serializer.writeString(levelType.getType());
        serializer.writeBoolean(reducedDebugInfo);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
