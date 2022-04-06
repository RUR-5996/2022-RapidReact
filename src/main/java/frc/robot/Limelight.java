package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    private static final double HUB_HEIGHT = 2.64; // meters

    private static final double MOUNTING_HEIGHT = 0;
    private static final double MOUNTING_ANGLE = 0;

    static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    static NetworkTableEntry angleX = table.getEntry("tx");
    static NetworkTableEntry angleY = table.getEntry("ty");

    public static double getDistanceToHub() {
        return Math.tan(angleX.getDouble(0.0) + MOUNTING_ANGLE)
                / (HUB_HEIGHT - MOUNTING_HEIGHT);
    }

    public static double getAngleToHub() {
        return angleY.getDouble(0.0);
    }
}
