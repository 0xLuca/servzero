package net.servzero.server.world.block;

public class BlockState {
    private int id;
    private int data;

    public BlockState(int id, int data) {
        this.id = id;
        this.data = data;
    }

    public int toGlobalId() {
        return (this.id << 4) | this.data;
    }

    public static BlockState fromGlobalId(int id) {
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
