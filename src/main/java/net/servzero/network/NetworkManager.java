package net.servzero.network;

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
    private Channel channel;
    private SocketAddress address;
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
        setProtocol(EnumProtocol.HANDSHAKING);
        Logger.info("New connection: " + this.channel.remoteAddress());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void channelRead0(ChannelHandlerContext ctx, Packet<PacketHandler> packet) {
        if (isConnected()) {
            if (this.packetHandler != null) {
                packet.handle(this.packetHandler);
            } else {
                Logger.error("Could not handle packet: No packet handler found");
            }
        }
    }

    public void sendPacket(Packet<?> packet) {
        if (this.isConnected()) {
            final EnumProtocol packetProtocol = EnumProtocol.getByPacket(packet);
            final EnumProtocol currentProtocol = this.channel.attr(NetworkManager.protocolAttributeKey).get();

            if (packetProtocol != currentProtocol) {
                this.channel.config().setAutoRead(false);
            }

            Runnable toExecute = () -> {
                if (packetProtocol != currentProtocol) {
                    this.setProtocol(packetProtocol);
                }

                this.channel.writeAndFlush(packet);
            };

            if (this.channel.eventLoop().inEventLoop()) {
                toExecute.run();
            } else {
                this.channel.eventLoop().execute(toExecute);
            }
        }
    }

    public void close() {
        this.channel.close();
    }

    private boolean isConnected() {
        return this.channel != null && this.channel.isOpen();
    }

    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getAddress() {
        return address;
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
