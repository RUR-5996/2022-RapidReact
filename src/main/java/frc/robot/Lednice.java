package frc.robot;

import edu.wpi.first.wpilibj.Timer;

public class Lednice {
    static final double INTAKE_CONSTANT = -0.65;

    static Timer buttonTimer = new Timer();

    static boolean highShooting = false;
    static boolean lowShooting = false;

    public static void periodic() {
        intake();
        shooter();
    }

    // Right bumper: reverse / out
    // Right trigger: intake (analog)
    public static void intake() {
        if (RobotMap.controller.getRightBumper()) {
            // Reverse
            RobotMap.intake.set(-INTAKE_CONSTANT);
        } else {
            // In
            double intakeSpeed = RobotMap.controller.getRightTriggerAxis() * INTAKE_CONSTANT;
            RobotMap.intake.set(intakeSpeed);
        }
    }

    public static void shooter() {
        if (RobotMap.controller.getBButtonPressed()) {
            highShooting = !highShooting;
            lowShooting = false;
        }
        if (RobotMap.controller.getAButtonPressed()) {
            lowShooting = !lowShooting;
            highShooting = false;
        }

        if (highShooting) {
            RobotMap.highShooter.set(0.8 * Variables.HIGH_SHOOTER_CONSTANT);
            RobotMap.lowShooter.set(-Variables.HIGH_SHOOTER_CONSTANT);
        } else if (lowShooting) {
            RobotMap.highShooter.set(0.5 * Variables.LOW_SHOOTER_CONSTANT);
            RobotMap.lowShooter.set(-1.2 * Variables.LOW_SHOOTER_CONSTANT);
        } else {
            RobotMap.highShooter.set(0);
            RobotMap.lowShooter.set(0);
        }
    }
}
