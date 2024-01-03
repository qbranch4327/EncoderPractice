package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;
 
public class AutonElevatorCommand extends Command{
    ElevatorSubsystem elevatorSubsystem;
    
    public AutonElevatorCommand(ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        addRequirements(elevatorSubsystem);
    }

    @Override
    public void initialize() {
        elevatorSubsystem.resetEncoders();
    }

    @Override
    public void execute() {
        // if (timer.get() == startTime)   {
        //     elevatorSubsystem.goUp(Data.n("upperDistance"));
        //     tiltSubsystem.tiltUp(Data.n("upperDegree"));
        // }
        elevatorSubsystem.goUp();
        
    }

    @Override
    public boolean isFinished() {
        if (elevatorSubsystem.encoderValue() < -2500)   {
            elevatorSubsystem.goDown();
            return true;
        }
        return false;
    }

}
