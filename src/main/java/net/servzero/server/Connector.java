package net.servzero.server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.servzero.logger.Logger;
import net.servzero.network.NetworkManager;
import net.servzero.network.packet.handler.InPacketHandshakeHandler;
import net.servzero.network.packet.serialization.PacketDecoder;
import net.servzero.network.packet.serialization.PacketEncoder;
import net.servzero.network.packet.serialization.PacketPrepender;
import net.servzero.network.packet.serialization.PacketSplitter;
import net.servzero.network.protocol.EnumProtocolDirection;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArrayList;

public class Connector {
    private CopyOnWriteArrayList<ChannelFuture> channels = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<NetworkManager> networkManagers = new CopyOnWriteArrayList<>();

    public void initialize(InetAddress address, int port) throws IOException {
        Class<? extends ServerSocketChannel> channelClass;
        EventLoopGroup eventLoopGroup;

        if (Epoll.isAvailable()) {
            channelClass = EpollServerSocketChannel.class;
            eventLoopGroup = new EpollEventLoopGroup(new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
            Logger.info("Using epoll channels");
        } else {
            channelClass = NioServerSocketChannel.class;
            eventLoopGroup = new NioEventLoopGroup(new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build());
            Logger.info("Using default channels");
        }

        new ServerBootstrap()
                .group(eventLoopGroup)
                .channel(channelClass)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.config().setOption(ChannelOption.TCP_NODELAY, true);

                        NetworkManager networkManager = new NetworkManager(EnumProtocolDirection.TO_SERVER);


                        channel.pipeline()
                                .addLast("splitter", new PacketSplitter())
                                .addLast("decoder", new PacketDecoder(EnumProtocolDirection.TO_SERVER))
                                .addLast("prepender", new PacketPrepender())
                                .addLast("encoder", new PacketEncoder(EnumProtocolDirection.TO_CLIENT));

                        channel.pipeline().addLast("packet_handler", networkManager);
                        networkManager.setPacketHandler(new InPacketHandshakeHandler(networkManager));
                    }
                })
                .localAddress(address, port)
                .bind()
                .syncUninterruptibly();

        Runtime.getRuntime().addShutdownHook(new Thread(eventLoopGroup::shutdownGracefully));
    }
}
