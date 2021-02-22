package net.servzero.network.ping;

import java.util.List;
import java.util.UUID;

public class PingResponse {
    private VersionInfo version;
    private PlayerInfo players;
    private Description description;
    private String favicon;

    public PingResponse(VersionInfo version, PlayerInfo playerInfo, Description description, String favicon) {
        this.version = version;
        this.players = playerInfo;
        this.description = description;
        this.favicon = favicon;
    }

    public static class VersionInfo {
        private String name;
        private int protocol;

        public VersionInfo(String name, int protocol) {
            this.name = name;
            this.protocol = protocol;
        }

        public String getName() {
            return name;
        }

        public int getProtocol() {
            return protocol;
        }
    }

    public static class PlayerInfo {
        private int max;
        private int online;
        private List<PlayerInfoItem> sample;

        public PlayerInfo(int max, int online, List<PlayerInfoItem> sample) {
            this.max = max;
            this.online = online;
            this.sample = sample;
        }

        public int getMax() {
            return max;
        }

        public int getOnline() {
            return online;
        }

        public List<PlayerInfoItem> getSample() {
            return sample;
        }
    }

    public static class PlayerInfoItem {
        private String name;
        private UUID id;

        public PlayerInfoItem(String name, UUID id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public UUID getId() {
            return id;
        }
    }

    public static class Description {
        private String text;

        public Description(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
