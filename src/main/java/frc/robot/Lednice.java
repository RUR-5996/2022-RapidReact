package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.sensors.BallPresence;

public class Lednice {
    enum Task {
        NONE,
        INTAKE,
        REVERSE,
        PREPARE_TO_SHOOT
    }

    enum Shooting {
        STOP,
        LOW,
        HIGH
    }

    static Timer intakeTimer = new Timer();

    static Task task = Task.NONE;
    static Shooting shooting = Shooting.STOP;

    public static void periodic() {
        intake();
        shooter();
    }

    private static void toggleTask(Task taskToToggle, boolean condition) {
        if (task == taskToToggle)
            task = Task.NONE;
        else
            task = taskToToggle;
    }

    // Right bumper: reverse / out
    // Right trigger: intake (analog)
    public static void intake() {
        double intakeSpeed = 0;

        boolean hasXButtonBeenPressed = RobotMap.controller.getXButtonPressed();

        toggleTask(Task.INTAKE, RobotMap.controller.getRightBumperPressed());
        toggleTask(Task.REVERSE, RobotMap.controller.getYButtonPressed());
        toggleTask(Task.PREPARE_TO_SHOOT, hasXButtonBeenPressed);

        switch (task) {
            case NONE:
                intakeSpeed = 0;
                break;
            case INTAKE:
                intakeSpeed = Constants.INTAKE_CONSTANT;
                break;
            case REVERSE:
                intakeSpeed = -Constants.INTAKE_CONSTANT;
                break;
            case PREPARE_TO_SHOOT:
                if (hasXButtonBeenPressed)
                    intakeTimer.reset();

                if (BallPresence.isTopBallPresent())
                    // If there is ball up top, move it down
                    intakeSpeed = -Constants.INTAKE_CONSTANT;

                break;
        }

        RobotMap.intake.set(intakeSpeed);
    }

    private static void toggleShooting(Shooting shootingToToggle, boolean condition) {
        if (shooting == shootingToToggle)
            shooting = Shooting.STOP;
        else
            shooting = shootingToToggle;
    }

    // A – low shooting toggle
    // B – high shooting toggle
    public static void shooter() {
        toggleShooting(Shooting.LOW, RobotMap.controller.getAButtonPressed());
        toggleShooting(Shooting.HIGH, RobotMap.controller.getBButtonPressed());

        // Set the speeds
        switch (shooting) {
            case STOP:
                RobotMap.shooterTop.set(0);
                RobotMap.shooterBottom.set(0);
                break;
            case LOW:
                RobotMap.shooterTop.set(Constants.LOW_SHOOTING_CONSTANT * Constants.TOP_SHOOTER_RATIO);
                RobotMap.shooterBottom.set(Constants.LOW_SHOOTING_CONSTANT * -1);
                break;
            case HIGH:
                RobotMap.shooterTop.set(Constants.HIGH_SHOOTING_CONSTANT * Constants.TOP_SHOOTER_RATIO);
                RobotMap.shooterBottom.set(Constants.HIGH_SHOOTING_CONSTANT * -1);
                break;
        }
    }
}
