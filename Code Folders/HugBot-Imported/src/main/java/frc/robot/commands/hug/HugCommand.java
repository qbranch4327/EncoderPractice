package frc.robot.commands.hug;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HugSubsystem;

public class HugCommand extends CommandBase {
    private final HugSubsystem hugSubsystem;

    public HugCommand(HugSubsystem hugSubsystem) {
        this.hugSubsystem = hugSubsystem;

        addRequirements(hugSubsystem);
    }

    @Override
    public void initialize() {
        hugSubsystem.hug();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
