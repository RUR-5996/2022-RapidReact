package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.*;

public class Lednice {
    enum Task {
        NONE,
        SHOOTING,
        REVERSE,
        PREPARE_TO_SHOOT,
        INTAKE,
    }

    enum Shooting {
        LOW,
        HIGH,
    }

    static Task task = Task.NONE;
    static Shooting shooting = Shooting.LOW;

    static boolean preparingWithBall = false;

    static Timer prepareTimer = new Timer();
    static Timer shootingTimer = new Timer();

    static final double INTAKE_CONSTANT = -0.7;
    static final double SHOOTER_LOW_CONSTANT = -0.55;
    static final double SHOOTER_HIGH_CONSTANT = -0.85;

    private static void toggleTask(Task taskToToggle, boolean condition) {
        shootingTimer.stop();
        shootingTimer.reset();

        prepareTimer.stop();
        prepareTimer.reset();

        preparingWithBall = false;

        if (condition) {
            if (task == taskToToggle)
                task = Task.NONE;
            else
                task = taskToToggle;
        }
    }

    public static void periodic() {
        SmartDashboard.putString("enum lednice", task.toString());
        if (RobotMap.controller.getXButtonPressed()) {
            toggleTask(Task.PREPARE_TO_SHOOT, true);
            shooting = Shooting.LOW;
        }

        if (RobotMap.controller.getYButtonPressed()) {
            toggleTask(Task.PREPARE_TO_SHOOT, true);
            shooting = Shooting.HIGH;
        }

        toggleTask(Task.INTAKE, RobotMap.controller.getRightBumperPressed());

        if (RobotMap.controller.getLeftBumper())
            task = Task.REVERSE;

        setMotors();
    }

    static void setMotors() {
        switch (task) {
            case NONE:
                RobotMap.intake.set(0);
                RobotMap.shooterTop.set(0);
                RobotMap.shooterBottom.set(0);
                break;

            case PREPARE_TO_SHOOT:
                if (BallCounter.isTopBallPresent()) {
                    prepareTimer.start();

                    preparingWithBall = true;

                    RobotMap.intake.set(-INTAKE_CONSTANT);
                    RobotMap.shooterTop.set(0);
                    RobotMap.shooterBottom.set(-INTAKE_CONSTANT);
                }

                // If started preparing with no top ball
                // or 0.5s has elapsed
                if (!preparingWithBall || prepareTimer.hasElapsed(0.5)) {
                    task = Task.SHOOTING;
                }

                break;

            case SHOOTING:
                shootingTimer.start();

                double speed = (shooting == Shooting.LOW)
                        ? SHOOTER_LOW_CONSTANT
                        : SHOOTER_HIGH_CONSTANT;

                RobotMap.shooterTop.set(speed);
                RobotMap.shooterBottom.set(speed);

                if (shootingTimer.hasElapsed(1))
                    RobotMap.intake.set(INTAKE_CONSTANT);
                else
                    RobotMap.intake.set(0);
                break;

            case REVERSE:
                RobotMap.intake.set(-INTAKE_CONSTANT);
                RobotMap.shooterTop.set(-INTAKE_CONSTANT);
                RobotMap.shooterBottom.set(-INTAKE_CONSTANT);
                break;

            case INTAKE:
                if (BallCounter.ballCount() == 0) {
                    RobotMap.intake.set(INTAKE_CONSTANT);
                    RobotMap.shooterTop.set(0);
                    RobotMap.shooterBottom.set(0);
                } else if (BallCounter.ballCount() == 2) {
                    RobotMap.intake.set(0);
                    RobotMap.shooterTop.set(0);
                    RobotMap.shooterBottom.set(0);
                } else if (BallCounter.isTopBallPresent()) {
                    RobotMap.intake.set(INTAKE_CONSTANT);
                    RobotMap.shooterTop.set(0);
                    RobotMap.shooterBottom.set(0);
                } else if (BallCounter.isBottomBallPresent()) {
                    RobotMap.intake.set(INTAKE_CONSTANT);
                    RobotMap.shooterTop.set(0);
                    RobotMap.shooterBottom.set(INTAKE_CONSTANT);
                }
                break;
        }

        if (BallCounter.ballCount() == 1 && !BallCounter.isBottomBallPresent()) {
            RobotMap.intake.set(INTAKE_CONSTANT);
            RobotMap.shooterTop.set(0);
            RobotMap.shooterBottom.set(0);
        }
    }
}
