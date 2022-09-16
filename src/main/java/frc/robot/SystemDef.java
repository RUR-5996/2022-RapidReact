package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Button; //check if imports are working
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

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
     * 
     * logitech:
     * trigger: shoot
     * two: move hood down
     * three: move hood up
     * four: rotate turret left
     * five: rotate turret right
     * six: intake in/out?
     * seven: reverse shooter
     * z-axis: positive-fast, negative-slow
     */

    public static XboxController controller = new XboxController(0);
    public static Joystick logitech = new Joystick(1);

    public static Button logitechTrigger = new JoystickButton(logitech, 1);
    public static final Button logitechTwo = new JoystickButton(logitech, 2);
    public static final Button logitechThree = new JoystickButton(logitech, 3);
    public static final Button logitechFour = new JoystickButton(logitech, 4);
    public static final Button logitechFive = new JoystickButton(logitech, 5);
    public static final Button logitechSix = new JoystickButton(logitech, 6);
    public static final Button logitechSeven = new JoystickButton(logitech, 7);

    public static WPI_TalonFX shooter = new WPI_TalonFX(9);//
    public static WPI_TalonFX intake = new WPI_TalonFX(10);
    public static WPI_VictorSPX turretMover = new WPI_VictorSPX(11);//
    public static WPI_VictorSPX feeder = new WPI_VictorSPX(12);
    public static WPI_VictorSPX hoodMotor = new WPI_VictorSPX(13);//

    // Pneumatics - later
    /*
     * public static Compressor compressor = new
     * Compressor(PneumaticsModuleType.CTREPCM);
     * public static DoubleSolenoid intakeSolenoid = new DoubleSolenoid(0,
     * PneumaticsModuleType.CTREPCM, 0, 1);
     */

}
