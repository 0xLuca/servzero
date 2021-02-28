package net.servzero.server.entity;

import net.servzero.network.serialization.DataSerializerType;

public class EntityMetadata<T> {
    private final byte index;
    private final DataSerializerType<T> serializer;
    private final T value;

    public EntityMetadata(int index, DataSerializerType<T> serializer, T value) {
        this.index = (byte) index;
        this.serializer = serializer;
        this.value = value;
    }

    public byte getIndex() {
        return index;
    }

    public DataSerializerType<T> getSerializer() {
        return serializer;
    }

    public T getValue() {
        return value;
    }
}
