package net.servzero.server.player;

import net.servzero.server.game.EnumChatMode;
import net.servzero.server.game.EnumMainHand;

import java.util.Locale;

public class ClientSettings {
    private Locale locale;
    private byte viewDistance;
    private EnumChatMode chatMode;
    private boolean chatColors;
    private short displayedSkinParts;
    private EnumMainHand mainHand;

    public ClientSettings() {
        this.locale = Locale.getDefault();
        this.viewDistance = 10;
        this.chatMode = EnumChatMode.ENABLED;
        this.chatColors = true;
        this.displayedSkinParts = 0x7F;
        this.mainHand = EnumMainHand.RIGHT;
    }

    public ClientSettings(Locale locale, byte viewDistance, EnumChatMode chatMode, boolean chatColors, short displayedSkinParts, EnumMainHand mainHand) {
        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatMode = chatMode;
        this.chatColors = chatColors;
        this.displayedSkinParts = displayedSkinParts;
        this.mainHand = mainHand;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public byte getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(byte viewDistance) {
        this.viewDistance = viewDistance;
    }

    public EnumChatMode getChatMode() {
        return chatMode;
    }

    public void setChatMode(EnumChatMode chatMode) {
        this.chatMode = chatMode;
    }

    public boolean isChatColors() {
        return chatColors;
    }

    public void setChatColors(boolean chatColors) {
        this.chatColors = chatColors;
    }

    public short getDisplayedSkinParts() {
        return displayedSkinParts;
    }

    public void setDisplayedSkinParts(byte displayedSkinParts) {
        this.displayedSkinParts = displayedSkinParts;
    }

    public EnumMainHand getMainHand() {
        return mainHand;
    }

    public void setMainHand(EnumMainHand mainHand) {
        this.mainHand = mainHand;
    }
}
