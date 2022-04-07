package frc.robot;

public class Shooting {
    public static double getSpeed(double distance) {
        distance = 4.5;
        // Delete the line above when finished, only a temporary solution
        return Math.sqrt(59.56 * Math.pow(distance, 2) * (1.428 * distance - 2.12))
                / (2.456 * distance - 4.24);
    }
}
