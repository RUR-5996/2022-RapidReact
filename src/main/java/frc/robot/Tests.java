package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Tests { // is this even running???

    // public static AHRS gyro = new AHRS(SPI.Port.kMXP);
    // public static AnalogInput absFl = new AnalogInput(0);
    // public static AnalogInput absFr = new AnalogInput(1);
    // public static AnalogInput absRl = new AnalogInput(2);
    // public static AnalogInput absRR = new AnalogInput(3);

    static double maxSpeed = -9999999; // in Rpm

    public static void init() {

    }

    public static void periodic() {

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
        /*
         * NeoSwerveDef.flModule.driveMotor.set(1);
         * double newMax = NeoSwerveDef.flModule.driveMotor.getEncoder().getVelocity();
         * 
         * if (newMax > maxSpeed) {
         * maxSpeed = newMax;
         * }
         */
    }

    public static double invertSensor(double input) {
        return voltageOverrun(5 - input);
    }

}
