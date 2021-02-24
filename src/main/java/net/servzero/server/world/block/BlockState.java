package net.servzero.server.world.block;

public class BlockState {
    private int id;
    private int data;

    public BlockState(int id, int data) {
        this.id = id;
        this.data = data;
    }

    public static int getGlobalPalleteIDFromState(BlockState state) {
        return (state.getId() << 4) | state.getData();
    }

    public static BlockState getStateFromGlobalPalleteId(int id) {
        return new BlockState(id >> 4, id & 0x0F);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
