package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.SpinnerSubsystem;

public class VomitCommand extends Command  {

    IntakeSubsystem intake;
    SpinnerSubsystem spinner;
    Timer timer = new Timer();

    public VomitCommand(IntakeSubsystem intake, SpinnerSubsystem spinner)   {
        this.intake = intake;
        this.spinner = spinner;
        addRequirements(intake);
        addRequirements(spinner);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        spinner.stop();
    }

    @Override
    public void execute() {
        spinner.stop();
        intake.vomit();
    }

    @Override
    public boolean isFinished() {
        if (timer.get() > 1)    {
            intake.intakeOff();
            return true;
        }
        else    {
            return false;
        }
    }
}
