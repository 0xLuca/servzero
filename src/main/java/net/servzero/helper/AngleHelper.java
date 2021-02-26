package net.servzero.helper;

public class AngleHelper {
    public static byte getAngleFromRotation(float rotation) {
        return (byte) ((rotation * 256.0F) / 360.0F);
    }
}
