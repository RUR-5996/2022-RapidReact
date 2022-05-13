package frc.robot.autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Lednice;
import frc.robot.RobotMap;
import frc.robot.Lednice.Task;

public class Simple {
    static Timer timer = new Timer();

    public static void init() {
        timer.reset();
        timer.start();
    }

    public static void periodic() {
        if (timer.hasElapsed(3) && !timer.hasElapsed(4)) {
            RobotMap.drive.arcadeDrive(0, -1);
        } else {
            RobotMap.drive.arcadeDrive(0, 0);
        }

        if (!timer.hasElapsed(3)) {
            Lednice.task = Task.AUTO;
        } else {
            Lednice.task = Task.NONE;
        }
        Lednice.setMotors();
    }
}
