package frc.robot;

import edu.wpi.first.wpilibj.Timer;

public class Lednice {
    static final double INTAKE_CONSTANT = -0.5;

    static Timer buttonTimer = new Timer();

    static boolean shooting = false;
    static boolean lowshooting = false;

    public static void periodic() {
        intake();
    }

    public static void intake() {
        if (RobotMap.controller.getRightBumper()) {
            RobotMap.intake.set(-0.5 * INTAKE_CONSTANT);
        } else {
            double intakeSpeed = RobotMap.controller.getRightTriggerAxis() * INTAKE_CONSTANT;
            RobotMap.intake.set(intakeSpeed);
        }
    }

    public static void shooter() {
        if (RobotMap.controller.getBButtonPressed()) {
            shooting = !shooting;
            lowshooting = false;
        }
        if (RobotMap.controller.getAButtonPressed()) {
            lowshooting = !lowshooting;
            shooting = false;
        }
        if (shooting) {
            RobotMap.highShooter.set(Variables.HIGH_SHOOTER_CONSTANT);
            RobotMap.lowShooter.set(Variables.HIGH_SHOOTER_CONSTANT);
        } else if (lowshooting) {
            RobotMap.highShooter.set(Variables.LOW_SHOOTER_CONSTANT);
            RobotMap.lowShooter.set(Variables.LOW_SHOOTER_CONSTANT);
        } else {
            RobotMap.highShooter.set(0);
            RobotMap.lowShooter.set(0);
        }

    }
}
