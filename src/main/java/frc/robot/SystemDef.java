package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Button; //check if imports are working
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SystemDef {

    /*
     * Joystick map:
     * xbox:
     * left stick: drive x-y
     * right stick: rotate
     * left trigger: intake
     * right trigger: outtake
     * left bumper: robot oriented drive
     * right bumper: field oriented drive
     * a-button: set wheel position
     * y-button: intake in/out
     * b-button: switch LLmode (driving/processing)
     * back button: reset gyro
     * 
     * logitech:
     * trigger: shoot
     * two: move hood down
     * three: move hood up
     * four: rotate turret left
     * five: rotate turret right
     * six: intake in/out?
     * seven: reverse shooter
     * eight: reset turret encoder
     * nine: set turret to manual mode
     * ten: set turret to LL mode
     * eleven: home turret
     * z-axis: defines the value of FeedSleep (-1 -> 1.5s, 1 -> 3s)
     */

    // Single instance of XboxController
    public static XboxController controller = new XboxController(0);

    // Logitech Attack 3 controller definition
    public static Joystick logitech = new Joystick(1);

    public static Button logitechTrigger = new JoystickButton(logitech, 1);
    public static final Button logitechTwo = new JoystickButton(logitech, 2);
    public static final Button logitechThree = new JoystickButton(logitech, 3);
    public static final Button logitechFour = new JoystickButton(logitech, 4);
    public static final Button logitechFive = new JoystickButton(logitech, 5);
    public static final Button logitechSix = new JoystickButton(logitech, 6);
    public static final Button logitechSeven = new JoystickButton(logitech, 7);
    public static final Button logitechEight = new JoystickButton(logitech, 8);
    public static final Button logitechNine = new JoystickButton(logitech, 9);
    public static final Button logitechTen = new JoystickButton(logitech, 10);
    public static final Button logitechEleven = new JoystickButton(logitech, 11);

    static Timer buttonTimer = new Timer(); // timer for identifying singule button presses

    /**
     * Initializing function for SystemDef
     * Holds timer init for further functions
     * Should be placed in robotInit()
     */
    public static void init() {
        buttonTimer.reset();
        buttonTimer.start();
    }

    /**
     * Alternative to XboxController's getPressed() for regular buttons
     * 
     * @param button Joystick buton on generic controller
     * @return boolean value determinig if the button has been pressed
     */
    public static boolean getPressed(Button button) { // TODO fix this
        if (button.get() && buttonTimer.get() >= 0.2) {
            buttonTimer.reset();
            return true;
        } else {
            return false;
        }
    }

    public static WPI_TalonFX shooter = new WPI_TalonFX(9); // Falcon motor with 1:1 ratio on shooter flywheel
    // public static WPI_TalonFX intake = new WPI_TalonFX(10); //probably deprecated
    // forever
    public static WPI_VictorSPX turretMover = new WPI_VictorSPX(11);// Redline motor with 64:1 sport gearbox
    public static WPI_VictorSPX feeder = new WPI_VictorSPX(12); // redline motor with 4:1 sport gearbox
    public static WPI_VictorSPX hoodMotor = new WPI_VictorSPX(13); // Redline motor with 64:1 sport gearbox
    public static CANSparkMax intakeSpark = new CANSparkMax(10, MotorType.kBrushless); // NEO motor spinning the intake

    // Pneumatics - later
    /*
     * public static Compressor compressor = new
     * Compressor(PneumaticsModuleType.CTREPCM);
     * public static DoubleSolenoid intakeSolenoid = new DoubleSolenoid(0,
     * PneumaticsModuleType.CTREPCM, 0, 1);
     */

}
