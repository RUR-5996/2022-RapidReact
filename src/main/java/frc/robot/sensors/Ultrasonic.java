package frc.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonic {
    AnalogInput sensor;

    public Ultrasonic(int port) {
        sensor = new AnalogInput(port);
    }

    public double getDistance() {
        return sensor.getVoltage() * 0.976;
    }
}
