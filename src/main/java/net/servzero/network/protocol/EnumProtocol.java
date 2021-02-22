package net.servzero.network.protocol;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import net.servzero.logger.Logger;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.in.InPacketHandshakeSetProtocol;
import net.servzero.network.packet.in.InPacketStatusStart;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public enum EnumProtocol {
    HANSHAKING(-1) {{
        this.addPacket(EnumProtocolDirection.TO_SERVER, InPacketHandshakeSetProtocol.class);
    }},
    PLAY(0),
    STATUS(1) {{
        this.addPacket(EnumProtocolDirection.TO_SERVER, InPacketStatusStart.class);
    }},
    LOGIN(2)
    ;

    public static EnumProtocol getById(int id) {
        return Arrays.stream(values()).filter(enumProtocol -> enumProtocol.id == id).findFirst().orElse(null);
    }

    private final int id;
    private final Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>> packets;

    EnumProtocol(int id) {
        this.id = id;
        this.packets = Maps.newEnumMap(EnumProtocolDirection.class);
    }

    public int getId() {
        return id;
    }

    public Optional<Packet<?>> getPacket(EnumProtocolDirection direction, int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends Packet<?>> cls = this.packets.get(direction).get(id);
        return Optional.ofNullable(cls == null ? null : cls.getDeclaredConstructor().newInstance());
    }

    public int getPacketId(EnumProtocolDirection direction, Packet<?> packet) {
        return this.packets.get(direction).inverse().getOrDefault(packet.getClass(), -1);
    }

    protected EnumProtocol addPacket(EnumProtocolDirection direction, Class<? extends Packet<?>> packet) {
        BiMap<Integer, Class<? extends Packet<?>>> map = this.packets.computeIfAbsent(direction, k -> HashBiMap.create());

        if (map.containsValue(packet)) {
            String error = "Could not register packet " + packet.getSimpleName() + ": already existing as id: " + map.inverse().get(packet);
            Logger.error(error);
            throw new IllegalArgumentException(error);
        } else {
            map.put(map.size(), packet);
            return this;
        }
    }
}
