package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Tests {

    public static AHRS gyro = new AHRS(SPI.Port.kMXP);
    public static AnalogInput absFl = new AnalogInput(0);
    public static AnalogInput absFr = new AnalogInput(1);
    public static AnalogInput absRl = new AnalogInput(2);
    public static AnalogInput absRR = new AnalogInput(3);

    public static void periodic() {
        SmartDashboard.putNumber("heading", gyro.getAngle());
        SmartDashboard.putNumber("NEO fl", NeoSwerveDef.flDrive.getEncoder().getPosition());
        SmartDashboard.putNumber("abs fl", absFl.getVoltage());
        SmartDashboard.putNumber("NEO fr", NeoSwerveDef.frDrive.getEncoder().getPosition());
        SmartDashboard.putNumber("abs fr", absFr.getVoltage());
        SmartDashboard.putNumber("NEO rl", NeoSwerveDef.rlDrive.getEncoder().getPosition());
        SmartDashboard.putNumber("abs rl", absRl.getVoltage());
        SmartDashboard.putNumber("NEO rr", NeoSwerveDef.rrDrive.getEncoder().getPosition());
        SmartDashboard.putNumber("abs rr", absRR.getVoltage());
    }

    public static void rotateToPosition() {
        double position = SmartDashboard.getNumber("rotateTo", 0); //in degs
        NeoSwerveDef.flModule.setAngle(position);
        NeoSwerveDef.frModule.setAngle(position);
        NeoSwerveDef.rlModule.setAngle(position);
        NeoSwerveDef.rrModule.setAngle(position);
    }

}
