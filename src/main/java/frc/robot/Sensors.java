package frc.robot;

import frc.robot.sensors.NavX;
import frc.robot.sensors.Ultrasonic;

public class Sensors {
    public static void periodic() {
        NavX.periodic();
        Ultrasonic.periodic();
    }
}
