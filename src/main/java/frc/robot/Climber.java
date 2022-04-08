package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
    enum Direction {
        NONE,
        UP,
        DOWN
    }

    static Direction clawsMoveable = Direction.NONE;
    static Direction clawsStatic = Direction.NONE;
    static boolean clawsOpen = false;
    static boolean armsOpen = false;

    static boolean lastPressedClaws = false;
    static boolean lastPressedArms = false;

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

        if (RobotMap.clawsToggle.get()) {
            if (!lastPressedClaws) {
                lastPressedClaws = true;
                clawsOpen = !clawsOpen;
            }
        } else {
            lastPressedClaws = false;
        }
        SmartDashboard.putBoolean("Claws", clawsOpen);

        if (RobotMap.armsToggle.get()) {
            if (!lastPressedArms) {
                lastPressedArms = true;
                armsOpen = !armsOpen;
            }
        } else {
            lastPressedArms = false;
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

        // if (RobotMap.rotateBackward.get())
        // RobotMap.climberRotate.set(CLIMBER_CONSTANT);
        // else if (RobotMap.rotateForward.get())
        // RobotMap.climberRotate.set(-CLIMBER_CONSTANT);
        // else
        // RobotMap.climberRotate.set(0);

        if (clawsOpen)
            RobotMap.claws.set(Value.kForward);
        else
            RobotMap.claws.set(Value.kReverse);

        if (armsOpen)
            RobotMap.arms.set(Value.kForward);
        else
            RobotMap.arms.set(Value.kReverse);

    }
}
