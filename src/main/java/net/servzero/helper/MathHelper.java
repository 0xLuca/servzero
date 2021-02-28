package net.servzero.helper;

import net.servzero.server.game.EnumBlockFace;
import net.servzero.server.world.block.Position;

public class MathHelper {
    public static double square(double number) {
        return number * number;
    }

    public static Position addToPosition(Position position, EnumBlockFace blockFace) {
        switch (blockFace) {
            case BOTTOM:
                return position.addY(-1);
            case TOP:
                return position.addY(1);
            case NORTH:
                return position.addZ(-1);
            case SOUTH:
                return position.addZ(1);
            case WEST:
                return position.addX(-1);
            case EAST:
                return position.addX(1);
        }
        return position;
    }
}
