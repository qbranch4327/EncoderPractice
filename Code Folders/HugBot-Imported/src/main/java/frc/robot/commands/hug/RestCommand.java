package frc.robot.commands.hug;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HugSubsystem;

public class RestCommand extends CommandBase {
    private final HugSubsystem hugSubsystem;

    public RestCommand(HugSubsystem hugSubsystem) {
        this.hugSubsystem = hugSubsystem;

        addRequirements(hugSubsystem);
    }

    @Override
    public void initialize() {
        hugSubsystem.rest();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
