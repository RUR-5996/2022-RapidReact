// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  static SwerveDrive SWERVE;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    SwerveDef.flModule.moduleInit();
    SwerveDef.frModule.moduleInit();
    SwerveDef.rlModule.moduleInit();
    SwerveDef.rrModule.moduleInit();

    SWERVE = SwerveDrive.getInstance();

    // SWERVE.init();

    Tests.init();
  }

  @Override
  public void robotPeriodic() {
    Tests.periodic();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    SwerveDef.flModule.enabledInit();
    SwerveDef.frModule.enabledInit();
    SwerveDef.rlModule.enabledInit();
    SwerveDef.rrModule.enabledInit();
    Turret.init();
    Intake.init();
  }

  @Override
  public void teleopPeriodic() {
    SwerveDrive.periodic(); // probably going to explode
    Turret.periodic();
    Intake.periodic();
  }

  @Override
  public void disabledInit() {
    SwerveDef.flModule.disabledInit();
    SwerveDef.frModule.disabledInit();
    SwerveDef.rlModule.disabledInit();
    SwerveDef.rrModule.disabledInit();
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
    SwerveDrive.testInit();
  }

  @Override
  public void testPeriodic() {
    SwerveDrive.zeroDrive();
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
