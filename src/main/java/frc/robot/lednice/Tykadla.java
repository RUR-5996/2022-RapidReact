package frc.robot.lednice;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class Tykadla {
    static boolean out = false;

    public static void periodic() {
        if (RobotMap.controller.getBackButtonPressed())
            out = !out;

        SmartDashboard.putBoolean("Tykadla", out);

        if (out) {
            RobotMap.tykadla.set(Value.kForward);
        } else {
            RobotMap.tykadla.set(Value.kReverse);
        }
    }
}
