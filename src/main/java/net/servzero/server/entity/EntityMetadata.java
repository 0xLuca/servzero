package net.servzero.server.entity;

public class EntityMetadata {
    private final byte index;
    private final Class fieldType;
    private final Object value;

    public <T> EntityMetadata(byte index, Class<T> fieldType, T value) {
        this.index = index;
        this.fieldType = fieldType;
        this.value = value;
    }


}
