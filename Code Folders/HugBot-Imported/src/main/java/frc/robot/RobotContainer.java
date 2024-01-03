// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.SwerveDriveCommand;
import frc.robot.commands.WalkCommand;
import frc.robot.commands.hug.HugCommand;
import frc.robot.commands.hug.ReleaseCommand;
import frc.robot.commands.hug.RestCommand;
import frc.robot.subsystems.HugSubsystem;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.WalkSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final XboxController controller = new XboxController(0);
  private final SwerveDrivetrain drivetrain = new SwerveDrivetrain();
  private final HugSubsystem hugSubsystem;
  private final WalkSubsystem walkSubsystem;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer(VictorSP hugController, VictorSP walkController) {
    // Repair these subsystems!
    hugSubsystem = new HugSubsystem(hugController);
    walkSubsystem = null;

    walkSubsystem.setDefaultCommand(new WalkCommand(walkSubsystem));
    drivetrain.setDefaultCommand(new SwerveDriveCommand(drivetrain, controller));

    Button hugButton = new JoystickButton(controller, XboxController.Button.kA.value);
    hugButton
      .whenPressed(new HugCommand(hugSubsystem))
      .whenReleased(new RestCommand(hugSubsystem));

    Button releaseButton = new JoystickButton(controller, XboxController.Button.kY.value);
    releaseButton
        .whenPressed(new ReleaseCommand(hugSubsystem))
        .whenReleased(new RestCommand(hugSubsystem));

    SendableRegistry.setSubsystem(hugController, "HugSubsystem");
    SendableRegistry.setSubsystem(walkController, "WalkSubsystem");
  }
}
