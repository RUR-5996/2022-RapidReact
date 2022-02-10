package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

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
}