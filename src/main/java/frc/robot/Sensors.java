package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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
    public static NetworkTableEntry pipeline = table.getEntry("pipeline");

    static double x = 0.0;
    static double y = 0.0;
    static double a = 0.0;

    public static LimelightMode currentMode = LimelightMode.DRIVING;

    // encoder stuff
    static Encoder turretEncoder = new Encoder(0, 1, true, CounterBase.EncodingType.k4X); // positive to the right,
                                                                                          // negative to the left
    static final double DEGREES_PER_TICK = 0.0991735537;
    static double turretTicks = 0;
    static double turretDegrees = 0;

    public static void periodic() {
        getLimeLight();
        getEncoder();

        if (SystemDef.controller.getBButtonPressed()) {
            switchLimelightMode();
        }

        if (SystemDef.logitechEight.get()) {
            resetEncoder();
        }

        report();
    }

    public static void init() {
        turretEncoder.setDistancePerPulse(DEGREES_PER_TICK);
        Shuffleboard.getTab("SmartDashboard").add(SwerveDef.gyro);
    }

    public static void report() {
        SmartDashboard.putString("LimelightMode", currentMode.toString());
        SmartDashboard.putNumber("tx", x);
        SmartDashboard.putNumber("ty", y);
        SmartDashboard.putNumber("ta", a);

        SmartDashboard.putNumber("encoderTicks", turretTicks);
        SmartDashboard.putNumber("turretDegrees", turretDegrees);

        SmartDashboard.putNumber("fl steer",
                SwerveDef.flModule.steerMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("fr steer",
                SwerveDef.frModule.steerMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("rr steer",
                SwerveDef.rrModule.steerMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("rl steer",
                SwerveDef.rlModule.steerMotor.getSelectedSensorPosition());

        SmartDashboard.putNumber("fl_sensor",
                SwerveDef.flModule
                        .clampContinuousDegs(SwerveDef.flModule.getBetterAnalogDegs() - SwerveDef.FL_STEER_OFFSET));
        SmartDashboard.putNumber("fr_sensor", SwerveDef.frModule
                .clampContinuousDegs(SwerveDef.frModule.getBetterAnalogDegs() - SwerveDef.FR_STEER_OFFSET));
        SmartDashboard.putNumber("rl_sensor", SwerveDef.rlModule
                .clampContinuousDegs(SwerveDef.rlModule.getBetterAnalogDegs() - SwerveDef.RL_STEER_OFFSET));
        SmartDashboard.putNumber("rr_sensor", SwerveDef.rrModule
                .clampContinuousDegs(SwerveDef.rrModule.getBetterAnalogDegs() - SwerveDef.RR_STEER_OFFSET));

        SmartDashboard.putNumber("gyro angle", SwerveDef.gyro.getRotation2d().getDegrees());

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
