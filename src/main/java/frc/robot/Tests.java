package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Tests {

    // public static AHRS gyro = new AHRS(SPI.Port.kMXP);
    // public static AnalogInput absFl = new AnalogInput(0);
    // public static AnalogInput absFr = new AnalogInput(1);
    // public static AnalogInput absRl = new AnalogInput(2);
    // public static AnalogInput absRR = new AnalogInput(3);

    static double maxSpeed = -9999999; // in Rpm

    public static void periodic() {
        SmartDashboard.putNumber("fl steer",
                SwerveDef.flModule.steerMotor.getSelectedSensorPosition()); // in degs
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
    }

    public static double voltageOverrun(double input) {
        if (input < 0) {
            return 5 + input;
        } else if (input > 5) {
            return input - 5;
        } else {
            return input;
        }
    }

    public static void rotateToPosition() {
        double position = SmartDashboard.getNumber("rotateTo", 0); // in degs
        SwerveDef.flModule.setAngle(position);
        SwerveDef.frModule.setAngle(position);
        SwerveDef.rlModule.setAngle(position);
        SwerveDef.rrModule.setAngle(position);
    }

    public static void measureSpeed() {
        NeoSwerveDef.flModule.driveMotor.set(1);
        double newMax = NeoSwerveDef.flModule.driveMotor.getEncoder().getVelocity();

        if (newMax > maxSpeed) {
            maxSpeed = newMax;
        }
    }

    public static double invertSensor(double input) {
        return voltageOverrun(5 - input);
    }

}
