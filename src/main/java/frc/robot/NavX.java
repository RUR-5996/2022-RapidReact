package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavX {
    static AHRS navX;

    public NavX() {
        navX = new AHRS(SPI.Port.kMXP);
    }

    public static void periodic() {
        xAcceleration();
        yAcceleration();
        getAngle();
    }

    public static void xAcceleration() {
        SmartDashboard.putNumber("X Acceleration", navX.getWorldLinearAccelX());
    }

    public static void yAcceleration() {
        SmartDashboard.putNumber("Y Acceleration", navX.getWorldLinearAccelY());
    }

    public static void getAngle() {
        SmartDashboard.putNumber("Angle", navX.getAngle());
    }
}
