package frc.robot.commands.hug;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HugSubsystem;

public class ReleaseCommand extends CommandBase {
    private final HugSubsystem hugSubsystem;

    public ReleaseCommand(HugSubsystem hugSubsystem) {
        this.hugSubsystem = hugSubsystem;

        addRequirements(hugSubsystem);
    }

    @Override
    public void initialize() {
        hugSubsystem.release();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
