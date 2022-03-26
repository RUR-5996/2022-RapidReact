package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Climber {
    enum Direction {
        NONE,
        UP,
        DOWN
    }

    enum Claws {
        OPEN,
        CLOSE
    }

    static Direction clawsMoveable = Direction.NONE;
    static Direction clawsStatic = Direction.NONE;
    static boolean clawsOpen = true;

    static boolean lastPressed = false;

    static final double CLIMBER_CONSTANT = 0.7;

    public static void periodic() {
        if (RobotMap.moveableUp.get())
            clawsMoveable = Direction.UP;
        else if (RobotMap.moveableDown.get())
            clawsMoveable = Direction.DOWN;
        else
            clawsMoveable = Direction.NONE;

        if (RobotMap.staticUp.get())
            clawsStatic = Direction.UP;
        else if (RobotMap.staticDown.get())
            clawsStatic = Direction.DOWN;
        else
            clawsStatic = Direction.NONE;

        if (RobotMap.clawsToggle.get() != lastPressed) {
            lastPressed = RobotMap.clawsToggle.get();
            clawsOpen = !clawsOpen;
        }

        switch (clawsMoveable) {
            case NONE:
                RobotMap.climberLeft.set(0);
                RobotMap.climberRight.set(0);
                break;
            case UP:
                RobotMap.climberLeft.set(CLIMBER_CONSTANT);
                RobotMap.climberRight.set(CLIMBER_CONSTANT);
                break;
            case DOWN:
                RobotMap.climberLeft.set(-CLIMBER_CONSTANT);
                RobotMap.climberRight.set(-CLIMBER_CONSTANT);
                break;
        }

        switch (clawsStatic) {
            case NONE:
                RobotMap.climberLarge.set(0);
                break;
            case UP:
                RobotMap.climberLarge.set(CLIMBER_CONSTANT);
                break;
            case DOWN:
                RobotMap.climberLarge.set(-CLIMBER_CONSTANT);
                break;
        }

        if (RobotMap.rotateBackward.get())
            RobotMap.climberRotate.set(CLIMBER_CONSTANT);
        else if (RobotMap.rotateForward.get())
            RobotMap.climberRotate.set(-CLIMBER_CONSTANT);
        else
            RobotMap.climberRotate.set(0);

        if (clawsOpen) {
            RobotMap.frontHook.set(Value.kForward);
            RobotMap.rearHook.set(Value.kForward);
        } else {
            RobotMap.frontHook.set(Value.kReverse);
            RobotMap.rearHook.set(Value.kReverse);
        }
    }
}
