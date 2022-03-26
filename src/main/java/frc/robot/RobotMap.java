package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Button;

public class RobotMap {
    // Controller
    public static final XboxController controller = new XboxController(0);
    public static final Joystick joystick = new Joystick(1);

    public static final Button moveableUp = new JoystickButton(joystick, 6);
    public static final Button moveableDown = new JoystickButton(joystick, 7);

    public static final Button staticUp = new JoystickButton(joystick, 11);
    public static final Button staticDown = new JoystickButton(joystick, 10);

    public static final Button rotateForward = new JoystickButton(joystick, 4);
    public static final Button rotateBackward = new JoystickButton(joystick, 5);

    public static final Button clawsToggle = new JoystickButton(joystick, 2);

    // Left
    static final WPI_TalonSRX frontLeft = new WPI_TalonSRX(2);
    static final WPI_TalonSRX rearLeft = new WPI_TalonSRX(1);
    // Right
    static final WPI_TalonSRX frontRight = new WPI_TalonSRX(3);
    static final WPI_TalonSRX rearRight = new WPI_TalonSRX(4);

    // Drive
    static final MotorControllerGroup leftControllerGroup = new MotorControllerGroup(rearLeft, frontLeft);
    static final MotorControllerGroup rightControllerGroup = new MotorControllerGroup(rearRight, frontRight);

    public static final DifferentialDrive drive = new DifferentialDrive(leftControllerGroup, rightControllerGroup);

    // Climber
    static final WPI_VictorSPX climberLarge = new WPI_VictorSPX(7);
    static final WPI_VictorSPX climberLeft = new WPI_VictorSPX(6);
    static final WPI_VictorSPX climberRight = new WPI_VictorSPX(5);
    static final WPI_VictorSPX climberRotate = new WPI_VictorSPX(8);

    // Intake
    public static final WPI_VictorSPX intake = new WPI_VictorSPX(12);

    public static final WPI_VictorSPX shooterBottom = new WPI_VictorSPX(11);
    public static final WPI_VictorSPX shooterTop = new WPI_VictorSPX(10);

    // Pneumatics
    // public static final Compressor compressor = new Compressor(ModuleType.kCTRE);
    public static final DoubleSolenoid leftIntake = new DoubleSolenoid(12, PneumaticsModuleType.CTREPCM, 1, 3);
    public static final DoubleSolenoid rightIntake = new DoubleSolenoid(12, PneumaticsModuleType.CTREPCM, 0, 2);

    // Sensors
    public static final AHRS navX = new AHRS(SPI.Port.kMXP);

    public static final DigitalInput ballButton = new DigitalInput(0);
    public static final ColorSensorV3 colorSensor = new ColorSensorV3(edu.wpi.first.wpilibj.I2C.Port.kOnboard);

    public static void climberInit() {
        climberLarge.setNeutralMode(NeutralMode.Brake);
        climberLeft.setNeutralMode(NeutralMode.Brake);
        climberRight.setNeutralMode(NeutralMode.Brake);
        climberRotate.setNeutralMode(NeutralMode.Brake);
    }
}