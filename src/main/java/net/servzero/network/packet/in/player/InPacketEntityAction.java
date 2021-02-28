package net.servzero.network.packet.in.player;

import com.google.gson.JsonSerializationContext;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.game.EnumEntityAction;

import java.io.IOException;

public class InPacketEntityAction implements Packet<AbstractInPacketPlayHandler> {
    private int entityId;
    private EnumEntityAction action;
    private int horseJumpBoost;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {
        this.entityId = serializer.readVarInt();
        this.action = serializer.readEnum(EnumEntityAction.class);
        this.horseJumpBoost = serializer.readVarInt();
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handleEntityAction(this);
    }

    public int getEntityId() {
        return entityId;
    }

    public EnumEntityAction getAction() {
        return action;
    }

    public int getHorseJumpBoost() {
        return horseJumpBoost;
    }
}
