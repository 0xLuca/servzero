package net.servzero.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import net.servzero.logger.Logger;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.protocol.EnumProtocol;
import net.servzero.network.protocol.EnumProtocolDirection;

import java.net.SocketAddress;

public class NetworkManager extends SimpleChannelInboundHandler<Packet<PacketHandler>> {
    public Channel channel;
    public SocketAddress address;
    private PacketHandler packetHandler;
    public static final AttributeKey<EnumProtocol> protocolAttributeKey = AttributeKey.valueOf("protocol");
    private EnumProtocolDirection protocolDirection;
    private EnumProtocol protocol;

    public NetworkManager(EnumProtocolDirection protocolDirection) {
        this.protocolDirection = protocolDirection;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.channel = ctx.channel();
        this.address = this.channel.remoteAddress();
        setProtocol(EnumProtocol.HANSHAKING);
        Logger.info("New connection: " + this.channel.remoteAddress());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void channelRead0(ChannelHandlerContext ctx, Packet<PacketHandler> packet) {
        if (this.channel.isOpen()) {
            if (this.packetHandler != null) {
                packet.handle(this.packetHandler);
            } else {
                Logger.error("Could not handle packet: No packet handler found");
            }
        }
    }

    public void setPacketHandler(PacketHandler handler) {
        this.packetHandler = handler;
    }

    public EnumProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(EnumProtocol protocol) {
        this.channel.attr(NetworkManager.protocolAttributeKey).set(protocol);
        this.channel.config().setAutoRead(true);
        this.protocol = protocol;
    }
}
