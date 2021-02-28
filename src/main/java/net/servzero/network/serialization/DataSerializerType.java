package net.servzero.network.serialization;

public class DataSerializerType<T> {
    private final int typeId;
    private final DataSerializer<T> dataSerializer;

    public DataSerializerType(int typeId, DataSerializer<T> dataSerializer) {
        this.typeId = typeId;
        this.dataSerializer = dataSerializer;
    }

    public int getTypeId() {
        return typeId;
    }

    public DataSerializer<T> getDataSerializer() {
        return dataSerializer;
    }
}
