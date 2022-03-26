package frc.robot.sensors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;

public class Ultrasonic {
    public double getDistance() {
        double distance = RobotMap.ultrasonic.getVoltage() * 0.976;
        SmartDashboard.putNumber("Ultrasonic", distance);
        return distance;
    }
}
