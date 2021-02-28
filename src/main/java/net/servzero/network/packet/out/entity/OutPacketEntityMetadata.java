package net.servzero.network.packet.out.entity;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.entity.EntityMetadata;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OutPacketEntityMetadata implements Packet<PacketHandler> {
    private final int entityId;
    private final List<EntityMetadata> metadataList;

    public OutPacketEntityMetadata(int entityId, EntityMetadata... metadata) {
        this(entityId, Arrays.asList(metadata));
    }

    public OutPacketEntityMetadata(int entityId, List<EntityMetadata> metadataList) {
        this.entityId = entityId;
        this.metadataList = metadataList;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeVarInt(this.entityId);
        metadataList.forEach(metadata -> {
            serializer.writeByte(metadata.getIndex());
            serializer.writeVarInt(metadata.getSerializer().getTypeId());
            metadata.getSerializer().getDataSerializer().write(serializer, metadata.getValue());
        });
        serializer.writeByte(0xff);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
