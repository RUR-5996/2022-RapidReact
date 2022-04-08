package frc.robot.lednice;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;

public class Tykadla {
    static boolean out = false;

    public static void periodic() {
        if (RobotMap.controller.getBackButtonPressed())
            out = !out;

        if (out) {
            RobotMap.leftIntake.set(Value.kForward);
            RobotMap.rightIntake.set(Value.kForward);
        } else {
            RobotMap.leftIntake.set(Value.kReverse);
            RobotMap.rightIntake.set(Value.kReverse);
        }
    }
}
