package net.servzero.network.packet.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.servzero.network.NetworkManager;
import net.servzero.network.packet.Packet;
import net.servzero.network.protocol.EnumProtocolDirection;
import net.servzero.server.Server;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PacketDecoder extends ByteToMessageDecoder {
    private final EnumProtocolDirection direction;

    public PacketDecoder(EnumProtocolDirection direction) {
        this.direction = direction;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> decoded) throws Exception {
        if (byteBuf.readableBytes() != 0) {
            PacketDataSerializer packetDataSerializer = new PacketDataSerializer(byteBuf);
            int packetId = packetDataSerializer.readVarInt();

            if (Server.IGNORED_PACKETS.contains(packetId)) {
                removeOverflow(packetDataSerializer);
                return;
            }
            Optional<? extends Packet<?>> optionalPacket = ctx.channel().attr(NetworkManager.protocolAttributeKey).get().getPacket(this.direction, packetId);
            if (optionalPacket.isEmpty()) {
                removeOverflow(packetDataSerializer);
                throw new IOException("Bad packet id: " + packetId);
            } else {
                Packet<?> packet = optionalPacket.get();
                packet.read(packetDataSerializer);
                if (packetDataSerializer.readableBytes() > 0) {
                    throw new IOException("Packet ID: " + packetId + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetDataSerializer.readableBytes() + " bytes extra whilst reading packet ID: " + packetId);
                } else {
                    decoded.add(packet);
                }
            }
        }
    }

    private void removeOverflow(ByteBuf message) {
        int length = message.readableBytes();
        message.readBytes(length);
    }
}