package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Sensors {

    // LL stuff
    static enum LimelightMode {
        PROCESSING,
        DRIVING
    }

    static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight"); // might need to change table
                                                                                         // name
    static NetworkTableEntry tx = table.getEntry("tx");
    static NetworkTableEntry ty = table.getEntry("ty");
    static NetworkTableEntry ta = table.getEntry("ta");
    static NetworkTableEntry pipeline = table.getEntry("pipeline");

    static double x = 0.0;
    static double y = 0.0;
    static double a = 0.0;

    static LimelightMode currentMode = LimelightMode.DRIVING;

    // encoder stuff
    static Encoder turretEncoder = new Encoder(0, 1, true, CounterBase.EncodingType.k4X); // positive to the right,
                                                                                          // negative to the left
    static final double DEGREES_PER_TICK = 100; // change
    static double turretTicks = 0;
    static double turretDegrees = 0;

    public static void periodic() {
        getLimeLight();

        if (SystemDef.controller.getBButtonPressed()) {
            switchLimelightMode();
        }

        if (SystemDef.getPressed(SystemDef.logitechEight)) {
            resetEncoder();
        }

        report();
    }

    public static void init() {
        turretEncoder.setDistancePerPulse(DEGREES_PER_TICK);
    }

    public static void report() {
        SmartDashboard.putString("LimelightMode", currentMode.toString());
        SmartDashboard.putNumber("tx", x);
        SmartDashboard.putNumber("ty", y);
        SmartDashboard.putNumber("ta", a);

        SmartDashboard.putNumber("encoderTicks", turretTicks);
        SmartDashboard.putNumber("turretDegrees", turretDegrees);
    }

    static void getLimeLight() {
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        a = ta.getDouble(0.0);
    }

    public static void switchLimelightMode() {
        switch (currentMode) {
            case PROCESSING:
                currentMode = LimelightMode.DRIVING;
                pipeline.setNumber(1); // driving cam
                break;
            case DRIVING:
                currentMode = LimelightMode.PROCESSING;
                pipeline.setNumber(0); // processing cam
                break;
            default:
                pipeline.setNumber(1);
                currentMode = LimelightMode.DRIVING;
                break;
        }
    }

    static void getEncoder() {
        turretTicks = turretEncoder.get();
        turretDegrees = turretEncoder.getDistance();
    }

    static void resetEncoder() {
        turretEncoder.reset();
    }

}
