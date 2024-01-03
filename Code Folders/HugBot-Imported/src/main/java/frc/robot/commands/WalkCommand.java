package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WalkSubsystem;

public class WalkCommand extends CommandBase {
    private final WalkSubsystem walkSubsystem;

    public WalkCommand(WalkSubsystem walkSubsystem) {
        this.walkSubsystem = walkSubsystem;

        addRequirements(walkSubsystem);
    }

    @Override
    public void execute() {
        // walkSubsystem.walk();
    }
}
