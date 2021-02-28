package net.servzero.network.packet.handler;

import net.servzero.network.NetworkHandler;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.InPacketHandshakeSetProtocol;
import net.servzero.network.protocol.EnumProtocol;

public class InPacketHandshakeHandler extends AbstractInPacketHandshakeHandler {

    public InPacketHandshakeHandler(NetworkHandler networkHandler) {
        super(networkHandler);
    }

    @Override
    public void handle(InPacketHandshakeSetProtocol packet) {
        this.networkHandler.setProtocolVersion(packet.getVersion());
        switch (packet.getProtocol()) {
            case LOGIN:
                this.networkHandler.setProtocol(EnumProtocol.LOGIN);
                this.networkHandler.setPacketHandler(new InPacketLoginHandler(this.networkHandler));
                // TODO: Implement login
                break;
            case STATUS:
                this.networkHandler.setProtocol(EnumProtocol.STATUS);
                InPacketStatusHandler handler = new InPacketStatusHandler(this.networkHandler);
                this.networkHandler.setPacketHandler(handler);
                break;
            default:
                throw new UnsupportedOperationException("Ivalid intention: " + packet.getProtocol());
        }
    }

    @Override
    public void handle(Packet<?> packet) {

    }
}
