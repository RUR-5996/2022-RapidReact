package frc.robot;

public class Sensors {
    public static void periodic() {
        getUltrasonicVoltage();
        getUltrasonicDistance();
    }

    static void getUltrasonicVoltage() {
        Variables.leftVoltage = RobotMap.ultrasonicLeft.getVoltage();
        Variables.rightVoltage = RobotMap.ultrasonicRight.getVoltage();
    }

    static void getUltrasonicDistance() {
        Variables.leftDistance = Variables.leftVoltage * Variables.VOLTS_TO_DISTANCE;
        Variables.rightDistance = Variables.rightVoltage * Variables.VOLTS_TO_DISTANCE;
    }

    static void getButton() {
        Variables.buttonState = RobotMap.ballCheck.get();
    }

    static void getColor() {
        RobotMap.ballSensor.getProximity();
    }
}
