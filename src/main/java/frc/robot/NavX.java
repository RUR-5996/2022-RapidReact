package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavX {
    static AHRS navX = new AHRS(SPI.Port.kMXP);

    public void periodic() {
        xAcceleration();
        yAcceleration();
        getAngle();
    }

    public void xAcceleration() {
        SmartDashboard.putNumber("X Acceleration", navX.getWorldLinearAccelX());
    }

    public void yAcceleration() {
        SmartDashboard.putNumber("Y Acceleration", navX.getWorldLinearAccelY());
    }

    public void getAngle() {
        SmartDashboard.putNumber("Angle", navX.getAngle());
    }
}
