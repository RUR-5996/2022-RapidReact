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
        if (!timer.hasElapsed(2)) {
            RobotMap.drive.arcadeDrive(0, -1);
            Lednice.task = Task.INTAKE;
        } else {
            RobotMap.drive.arcadeDrive(0, 0);
            Lednice.task = Task.NONE;
        }
    }
}
