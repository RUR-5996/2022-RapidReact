package frc.robot;

public class Climber {
    enum Direction {
        NONE,
        UP,
        DOWN
    }

    static Direction clawsMoveable = Direction.NONE;
    static Direction clawsStatic = Direction.NONE;

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

        switch (clawsMoveable) {
            case NONE:
                RobotMap.climberLeft.set(0);
                RobotMap.climberRight.set(0);
            case UP:
                RobotMap.climberLeft.set(CLIMBER_CONSTANT);
                RobotMap.climberRight.set(CLIMBER_CONSTANT);
            case DOWN:
                RobotMap.climberLeft.set(-CLIMBER_CONSTANT);
                RobotMap.climberRight.set(-CLIMBER_CONSTANT);
        }

        switch (clawsStatic) {
            case NONE:
                RobotMap.climberLarge.set(0);
            case UP:
                RobotMap.climberLarge.set(CLIMBER_CONSTANT);
            case DOWN:
                RobotMap.climberLarge.set(-CLIMBER_CONSTANT);
        }

        if (RobotMap.rotateBackward.get())
            RobotMap.climberRotate.set(CLIMBER_CONSTANT);
        else if (RobotMap.rotateBackward.get())
            RobotMap.climberRotate.set(CLIMBER_CONSTANT);
        else
            RobotMap.climberRotate.set(0);
    }
}
