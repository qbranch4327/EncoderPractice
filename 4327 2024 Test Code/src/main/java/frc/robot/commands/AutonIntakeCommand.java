package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Timer;

public class AutonIntakeCommand extends Command{
    IntakeSubsystem intakeSubsystem;
    Swerve swerve;
    Timer timer;
    double startTime = 0;
    double endTime = 0;
    boolean position = true;
    
    public AutonIntakeCommand(IntakeSubsystem intakeSubsystem, Swerve swerve) {
        this.intakeSubsystem = intakeSubsystem;
        this.swerve = swerve;
        timer = new Timer();
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        intakeSubsystem.intakeOn(true);
        // swerve.reverseGyro();
    }

    @Override
    public boolean isFinished() {
        if (timer.get() > 1) {
            intakeSubsystem.intakeOff();
            intakeSubsystem.grab();
            return true;
        }
        return false;
    }

}
