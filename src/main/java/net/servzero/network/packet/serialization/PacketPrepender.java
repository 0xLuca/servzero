package net.servzero.network.packet.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class PacketPrepender extends MessageToByteEncoder<ByteBuf> {

    protected void encode(ChannelHandlerContext ctx, ByteBuf from, ByteBuf to) throws Exception {
        int length = from.readableBytes();
        int bytes = PacketDataSerializer.countBytes(length);
        if (bytes > 3) {
            throw new IllegalArgumentException("unable to fit " + length + " into " + 3);
        } else {
            PacketDataSerializer packetDataSerializer = new PacketDataSerializer(to);
            packetDataSerializer.ensureWritable(bytes + length);
            packetDataSerializer.writeVarInt(length);
            packetDataSerializer.writeBytes(from, from.readerIndex(), length);
        }
    }
}
