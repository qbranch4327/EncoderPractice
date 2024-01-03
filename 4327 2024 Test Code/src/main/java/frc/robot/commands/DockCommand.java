package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;

public class DockCommand extends Command{
    
    Swerve swerve;
    boolean docked = false;
    boolean start = false ;
    double offset;

    public DockCommand(Swerve swerve)    {
        this.swerve = swerve;
    }

    @Override
    public void initialize() {
        start = false;
        offset = swerve.getPitch();
    }

    @Override
    public void execute() {
        
        if (!start){
            swerve.drive(false);
        }
        if (swerve.getPitch() > 13 + offset)    {
            swerve.slowDown(false);
            start = true;
        }
    }

    @Override
    public boolean isFinished(){
        if (swerve.getPitch() < 12 + offset && start)    {
            swerve.xStance();
            swerve.stop();
            start = false;
            return true;
        }
        else{
            return false;
        }
    }
}
