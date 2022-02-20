package frc.robot;

import edu.wpi.first.wpilibj.Timer;

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

    static Timer buttonTimer = new Timer();

    static Task task = Task.NONE;
    static Shooting shooting = Shooting.STOP;

    static boolean hasButtonStopped = false;

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

        toggleTask(Task.INTAKE, RobotMap.controller.getRightBumperPressed());
        toggleTask(Task.REVERSE, RobotMap.controller.getYButtonPressed());
        toggleTask(Task.PREPARE_TO_SHOOT, RobotMap.controller.getXButtonPressed());

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
                intakeSpeed = 0;
                break;
        }

        RobotMap.intake.set(intakeSpeed);
    }

    // A – low shooting toggle
    // B – high shooting toggle
    public static void shooter() {
        // Check if A-button is pressed
        if (RobotMap.controller.getAButtonPressed()) {
            switch (shooting) {
                // If the shooter is already shooting low, stop
                case LOW:
                    shooting = Shooting.STOP;
                    break;
                // Otherwise, switch to low
                default:
                    shooting = Shooting.LOW;
            }
        }

        // Same principle as above
        if (RobotMap.controller.getBButtonPressed()) {
            switch (shooting) {
                case HIGH:
                    shooting = Shooting.STOP;
                    break;
                default:
                    shooting = Shooting.HIGH;
            }
        }

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
