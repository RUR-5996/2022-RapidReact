package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors {

    static double encoderTickRight = 0;
    static double encoderTickLeft = 0;

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

    static void getTicks() {
        encoderTickRight = RobotMap.rearLeft.getSelectedSensorPosition(0);
        encoderTickLeft = RobotMap.frontRight.getSelectedSensorPosition(0);
        SmartDashboard.putNumber("leftticks", encoderTickLeft);
        SmartDashboard.putNumber("rightticks", encoderTickRight);
    }
}
