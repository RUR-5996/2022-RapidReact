package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.*;

public class Lednice {
    public enum Task {
        NONE,
        SHOOTING,
        REVERSE,
        INTAKE,
        AUTO,
    }

    enum Shooting {
        LOW,
        HIGH,
    }

    public static Task task = Task.NONE;
    static Shooting shooting = Shooting.HIGH;

    static Task previousTask = Task.NONE;

    static Timer shootingTimer = new Timer();

    static final double INTAKE_CONSTANT = -0.35;

    static double shooterSpeed;

    public static void periodic() {
        SmartDashboard.putString("Enum lednice", task.toString());

        if (RobotMap.controller.getXButton()) {
            task = Task.SHOOTING;
            shooting = Shooting.LOW;
        } else if (RobotMap.controller.getYButton()) {
            shooting = Shooting.HIGH;
            task = Task.SHOOTING;
        } else if (RobotMap.controller.getRightBumper()) {
            task = Task.INTAKE;
        } else {
            task = Task.NONE;
        }

        shooterSpeed = 0.15 + 0.6 * (RobotMap.joystick.getZ() + 1) / 2;
        SmartDashboard.putNumber("Shooter speed", shooterSpeed);

        double triggerLeft = RobotMap.controller.getLeftTriggerAxis();
        if (triggerLeft > 0.2)
            task = Task.REVERSE;

        setMotors();
    }

    public static void setMotors() {
        switch (task) {
            case NONE:
                RobotMap.intake.set(0);
                RobotMap.shooterTop.set(0);
                RobotMap.shooterBottom.set(0);
                break;

            case SHOOTING:
                RobotMap.shooterTop.set(shooterSpeed);
                RobotMap.shooterBottom.set(
                        shooterSpeed * (shooting == Shooting.LOW ? 1 : 0.8));

                RobotMap.intake.set(INTAKE_CONSTANT);
                break;

            case REVERSE:
                RobotMap.intake.set(-INTAKE_CONSTANT);
                RobotMap.shooterTop.set(INTAKE_CONSTANT);
                RobotMap.shooterBottom.set(INTAKE_CONSTANT);
                break;

            case INTAKE:
                RobotMap.intake.set(INTAKE_CONSTANT);
                RobotMap.shooterTop.set(0);
                RobotMap.shooterBottom.set(-INTAKE_CONSTANT * 1.2);
                break;

            case AUTO:
                RobotMap.intake.set(INTAKE_CONSTANT);
                RobotMap.shooterBottom.set(-INTAKE_CONSTANT * 1.2);
                break;
        }
    }
}
