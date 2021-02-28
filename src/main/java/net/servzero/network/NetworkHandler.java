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
import net.servzero.server.Server;
import net.servzero.server.player.Player;
import net.servzero.server.player.PlayerLogoutManager;

import java.net.SocketAddress;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet<PacketHandler>> {
    private Player owner = null;

    private Channel channel;
    private SocketAddress address;
    private PacketHandler packetHandler;
    public static final AttributeKey<EnumProtocol> protocolAttributeKey = AttributeKey.valueOf("protocol");
    private EnumProtocolDirection protocolDirection;
    private EnumProtocol protocol;

    private int protocolVersion;

    public NetworkHandler(EnumProtocolDirection protocolDirection) {
        this.protocolDirection = protocolDirection;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.channel = ctx.channel();
        this.address = this.channel.remoteAddress();
        setProtocol(EnumProtocol.HANDSHAKING);
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

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if (this.owner != null) {
            // TODO: Add leave
            Server.getInstance().unregisterPlayer(owner);
            PlayerLogoutManager.handleLogout(owner);
        }
    }

    public void sendPacket(Packet<?> packet) {
        if (this.isConnected()) {
            final EnumProtocol packetProtocol = EnumProtocol.getByPacket(packet);
            final EnumProtocol currentProtocol = this.channel.attr(NetworkHandler.protocolAttributeKey).get();

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

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
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
        this.channel.attr(NetworkHandler.protocolAttributeKey).set(protocol);
        this.channel.config().setAutoRead(true);
        this.protocol = protocol;
    }
}
