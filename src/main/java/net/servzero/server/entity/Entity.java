package net.servzero.server.entity;

import net.servzero.server.world.Location;

public class Entity {
    private static volatile int entityIdCounter = 0;

    private static synchronized int generateNewEntityId() {
        return entityIdCounter++;
    }

    private final int id;
    private Location location;

    public Entity() {
        this.id = generateNewEntityId();
    }

    public int getId() {
        return id;
    }
}
