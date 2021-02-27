package net.servzero.server.world.block;

import net.servzero.network.packet.out.OutPacketBlockChange;
import net.servzero.server.world.IUpdateable;
import net.servzero.server.world.Location;
import net.servzero.server.world.World;

public class Block implements IUpdateable {
    private final World world;
    private final Position position;
    private BlockState state;

    public Block(World world, Position position, BlockState material) {
        this.world = world;
        this.position = position;
        this.state = material;
    }

    public World getWorld() {
        return world;
    }

    public Position getPosition() {
        return position;
    }

    public BlockState getState() {
        return state;
    }

    public synchronized void setType(BlockState state) {
        this.state = state;
        update();
    }

    @Override
    public void update() {
        this.world.sendToAllInWorld(new OutPacketBlockChange(this));
    }
}
