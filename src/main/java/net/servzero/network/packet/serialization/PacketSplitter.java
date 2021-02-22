package net.servzero.network.packet.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

public class PacketSplitter extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> splitted) {
        byteBuf.markReaderIndex();
        byte[] buffer = new byte[3];

        for (int i = 0; i < buffer.length; ++i) {
            if (!byteBuf.isReadable()) {
                byteBuf.resetReaderIndex();
                return;
            }

            buffer[i] = byteBuf.readByte();
            if (buffer[i] >= 0) {
                PacketDataSerializer packetDataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(buffer));

                try {
                    int length = packetDataSerializer.readVarInt();

                    if (byteBuf.readableBytes() >= length) {
                        splitted.add(byteBuf.readBytes(length));
                        return;
                    }

                    byteBuf.resetReaderIndex();
                } finally {
                    packetDataSerializer.release();
                }
                return;
            }
        }
        throw new CorruptedFrameException("length wider than 21-bit");
    }
}