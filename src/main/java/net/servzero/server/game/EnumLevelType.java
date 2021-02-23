package net.servzero.server.game;

public enum EnumLevelType {
    DEFAULT("default"),
    FLAT("flat"),
    LARGE_BIOMES("largeBiomes"),
    AMPLIFIED("amplified"),
    DEFAULT_1_1("default_1_1")
    ;

    private String type;

    EnumLevelType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
