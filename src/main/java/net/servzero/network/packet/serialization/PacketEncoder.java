package net.servzero.network.packet.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.servzero.network.NetworkManager;
import net.servzero.network.packet.Packet;
import net.servzero.network.protocol.EnumProtocol;
import net.servzero.network.protocol.EnumProtocolDirection;
import org.apache.log4j.Logger;

import java.io.IOException;

public class PacketEncoder extends MessageToByteEncoder<Packet<?>> {
    private static final Logger a = net.servzero.logger.Logger.getLogger();
    private static final int MAX_PACKET_SIZE = 2097152;
    private final EnumProtocolDirection direction;

    public PacketEncoder(EnumProtocolDirection direction) {
        this.direction = direction;
    }

    protected void encode(ChannelHandlerContext channelhandlercontext, Packet<?> packet, ByteBuf byteBuf) throws Exception {
        EnumProtocol protocol = channelhandlercontext.channel().attr(NetworkManager.protocolAttributeKey).get();
        int packetId = protocol.getPacketId(this.direction, packet);

        if (packetId < 0) {
            throw new IOException("Can't serialize unregistered packet");
        } else {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(byteBuf);
            packetdataserializer.writeVarInt(packetId);

            try {
                packet.write(packetdataserializer);
            } catch (Throwable throwable) {
                PacketEncoder.a.error(throwable);
            }

            int packetLength = byteBuf.readableBytes();
            if (packetLength > MAX_PACKET_SIZE) {
                throw new PacketTooLargeException(packet, packetLength);
            }
        }
    }

    public static class PacketTooLargeException extends RuntimeException {
        private final Packet<?> packet;

        PacketTooLargeException(Packet<?> packet, int packetLength) {
            super("PacketTooLarge - " + packet.getClass().getSimpleName() + " is " + packetLength + ". Max is " + MAX_PACKET_SIZE);
            this.packet = packet;
        }

        public Packet<?> getPacket() {
            return packet;
        }
    }
}
