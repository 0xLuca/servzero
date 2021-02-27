package net.servzero.helper;

import net.servzero.server.world.block.Position;

public class Validator {
    public static void validateCoordinate(Position position) {
        if (position.getY() > 256) {
            throw new IllegalArgumentException("Cannot use coordinate with Y > 256");
        }
    }
}
