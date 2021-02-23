package net.servzero.network.packet.in;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.handler.AbstractInPacketPlayHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.game.EnumChatMode;
import net.servzero.server.game.EnumHand;
import net.servzero.server.player.ClientSettings;

import java.util.Locale;

public class InPacketClientSettings implements Packet<AbstractInPacketPlayHandler> {
    private Locale locale;
    private byte viewDistance;
    private EnumChatMode chatMode;
    private boolean chatColors;
    private short displayedSkinParts;
    private EnumHand mainHand;

    @Override
    public void read(PacketDataSerializer serializer) {
        this.locale = Locale.forLanguageTag(serializer.readString(16));
        this.viewDistance = serializer.readByte();
        this.chatMode = EnumChatMode.getById(serializer.readVarInt());
        this.chatColors = serializer.readBoolean();
        this.displayedSkinParts = serializer.readUnsignedByte();
        this.mainHand = EnumHand.getById(serializer.readVarInt());
    }

    @Override
    public void write(PacketDataSerializer serializer) {

    }

    @Override
    public void handle(AbstractInPacketPlayHandler handler) {
        handler.handleClientSettings(this);
    }

    public ClientSettings toClientSettings() {
        return new ClientSettings(locale, viewDistance, chatMode, chatColors, displayedSkinParts, mainHand);
    }
}
