package net.servzero.network.packet.handler;

import net.servzero.network.NetworkManager;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.InPacketHandshakeSetProtocol;
import net.servzero.network.protocol.EnumProtocol;

public class InPacketHandshakeHandler extends AbstractInPacketHandshakeHandler {

    public InPacketHandshakeHandler(NetworkManager networkManager) {
        super(networkManager);
    }

    @Override
    public void handle(InPacketHandshakeSetProtocol packet) {
        switch (packet.getProtocol()) {
            case LOGIN:
                this.networkManager.setProtocol(EnumProtocol.LOGIN);
                //TODO: Implement login
                break;
            case STATUS:
                this.networkManager.setProtocol(EnumProtocol.STATUS);
                InPacketStatusStartHandler handler = new InPacketStatusStartHandler(this.networkManager);
                handler.setClientVersion(packet.getVersion());
                this.networkManager.setPacketHandler(handler);
                break;
            default:
                throw new UnsupportedOperationException("Ivalid intention: " + packet.getProtocol());
        }
    }

    @Override
    public void handle(Packet<?> packet) {

    }
}
