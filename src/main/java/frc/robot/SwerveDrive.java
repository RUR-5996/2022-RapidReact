package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.XboxController;

public class SwerveDrive {

    public static XboxController controller = new XboxController(0);
    
    private static SwerveDrive SWERVE = new SwerveDrive();
    private static double xSpeed = 0;
    private static double ySpeed = 0;
    private static double rotation = 0;

    static final Translation2d FL_LOC = new Translation2d(SwerveDef.WHEEL_BASE_WIDTH/2, SwerveDef.TRACK_WIDTH/2);
    static final Translation2d FR_LOC = new Translation2d(SwerveDef.WHEEL_BASE_WIDTH/2, SwerveDef.TRACK_WIDTH/2);
    static final Translation2d RL_LOC = new Translation2d(SwerveDef.WHEEL_BASE_WIDTH/2, SwerveDef.TRACK_WIDTH/2);
    static final Translation2d RR_LOC = new Translation2d(SwerveDef.WHEEL_BASE_WIDTH/2, SwerveDef.TRACK_WIDTH/2);
    public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(FL_LOC, FR_LOC, RL_LOC, RR_LOC);

    public static SwerveDrive getInstance() {
        return SWERVE;
    }

    public static void periodic() {
        drive();
    }

    public static void drive() {
        xSpeed = controller.getLeftX()*SwerveDef.MAX_WHEEL_SPEED;
        ySpeed = controller.getLeftY()*SwerveDef.MAX_WHEEL_SPEED;
        rotation = controller.getRightX()*SwerveDef.MAX_WHEEL_SPEED;

        SwerveModuleState[] states = swerveKinematics.toSwerveModuleStates(new ChassisSpeeds(xSpeed, ySpeed, rotation));
        SwerveDef.flModule.setState(states[0]);
        SwerveDef.frModule.setState(states[1]);
        SwerveDef.rlModule.setState(states[2]);
        SwerveDef.rrModule.setState(states[3]);
    }
}
