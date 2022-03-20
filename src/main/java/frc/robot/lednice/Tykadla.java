package frc.robot.lednice;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;

public class Tykadla {
    static boolean out = false;
    
    public static void periodic() {
        if (RobotMap.controller.getPOV() == 90) { // D-pad right
            out = true;
        } else if (RobotMap.controller.getPOV() == 270) { // D-pad left
            out = false;
        }

        if (out) {
            RobotMap.leftIntake.set(Value.kForward);
            RobotMap.rightIntake.set(Value.kForward);
        } else {
            RobotMap.leftIntake.set(Value.kReverse);
            RobotMap.rightIntake.set(Value.kReverse);
        }
    }
}
