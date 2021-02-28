package net.servzero.server.inventory;

import net.servzero.network.packet.serialization.ISerializable;
import net.servzero.network.packet.serialization.PacketDataSerializer;
import net.servzero.server.inventory.item.Material;

public class ItemStack implements ISerializable<PacketDataSerializer> {
    private Material material;
    private short damage;
    private int count;

    public ItemStack(Material material, int damage, int count) {
        this.material = material;
        this.damage = (short) damage;
        this.count = count;
    }

    private boolean isPresent() {
        return this.material.getId() != Material.AIR.getId();
    }

    public Material getMaterial() {
        return this.material;
    }

    public short getData() {
        return damage;
    }

    public int getCount() {
        return this.count;
    }

    public boolean hasNbt() {
        return false;
    }
}
