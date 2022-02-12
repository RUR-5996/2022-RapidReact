package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C.Port;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class RobotMap {
    // Controller
    public static final XboxController controller = new XboxController(0);

    // Left
    static final WPI_TalonSRX frontLeft = new WPI_TalonSRX(0);
    static final WPI_TalonSRX rearLeft = new WPI_TalonSRX(1);
    // Right
    static final WPI_TalonSRX frontRight = new WPI_TalonSRX(2);
    static final WPI_TalonSRX rearRight = new WPI_TalonSRX(3);

    // Drive
    static final MotorControllerGroup leftControllerGroup = new MotorControllerGroup(rearLeft, frontLeft);
    static final MotorControllerGroup rightControllerGroup = new MotorControllerGroup(rearRight, frontRight);

    public static final DifferentialDrive drive = new DifferentialDrive(leftControllerGroup, rightControllerGroup);

    // Intake
    public static final WPI_VictorSPX intake = new WPI_VictorSPX(8);

    public static final WPI_VictorSPX lowShooter = new WPI_VictorSPX(5);
    public static final WPI_VictorSPX highShooter = new WPI_VictorSPX(6);

    public static final DigitalInput ballCheck = new DigitalInput(0);
    public static final ColorSensorV3 ballSensor = new ColorSensorV3(Port.kOnboard);

    public static final AnalogInput ultrasonicLeft = new AnalogInput(0);
    public static final AnalogInput ultrasonicRight = new AnalogInput(1);
}