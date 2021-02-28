package net.servzero.network.packet.serialization;

import java.io.IOException;

public interface ISerializable<T> {
    default void read(T t) throws IOException {}
    default void write(T t) throws IOException {}
}
