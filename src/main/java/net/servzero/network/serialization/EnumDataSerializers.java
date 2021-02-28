package net.servzero.network.serialization;

import net.servzero.network.packet.serialization.PacketDataSerializer;

public interface EnumDataSerializers {
    DataSerializerType<Byte> BYTE = new DataSerializerType<>(0, new DataSerializer<>() {
        @Override
        public Byte read(PacketDataSerializer serializer) {
            return serializer.readByte();
        }

        @Override
        public void write(PacketDataSerializer serializer, Byte value) {
            serializer.writeByte(value);
        }
    });
}
