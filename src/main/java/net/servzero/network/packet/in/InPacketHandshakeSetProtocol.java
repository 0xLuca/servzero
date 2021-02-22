package net.servzero.network.packet.in;

import net.servzero.network.protocol.EnumProtocol;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.InPacketHandshakeHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class InPacketHandshakeSetProtocol implements Packet<InPacketHandshakeHandler> {
    private int version;
    private String hostname;
    private int port;
    private EnumProtocol protocol;

    @Override
    public void read(PacketDataSerializer serializer) {
        this.version = serializer.readVarInt();
        this.hostname = serializer.readString(255);
        this.port = serializer.readUnsignedShort();
        this.protocol = EnumProtocol.getById(serializer.readVarInt());
    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeVarInt(version);
        serializer.writeString(hostname);
        serializer.writeShort(port);
        serializer.writeVarInt(protocol.ordinal());
    }

    public int getVersion() {
        return version;
    }

    public EnumProtocol getProtocol() {
        return protocol;
    }

    @Override
    public void handle(InPacketHandshakeHandler handler) {
        handler.handle(this);
    }
}
