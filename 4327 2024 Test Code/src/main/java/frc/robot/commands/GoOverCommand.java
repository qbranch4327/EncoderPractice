package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;

public class GoOverCommand extends Command {
    
    Swerve swerve;
    Timer timer;
    boolean started = false;
    boolean wentOver = false;
    boolean returning = false;
    boolean out = false;

    public GoOverCommand(Swerve swerve)  {
        timer = new Timer();
        this.swerve = swerve;
    }

    @Override
    public void initialize() {
        timer.reset();
        swerve.drive(false);
        started = false;
        wentOver = false;
        returning = false;
        out = false;
    }

    @Override
    public void execute()   {
        if (swerve.getRoll() > 13)  {
            started = true;
        }
        if (swerve.getRoll() < -13 && started){
            wentOver = true;
        }
        if (swerve.getRoll() > -1 && wentOver && !out)    {
            timer.start();
            swerve.drive(false);
            out = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (timer.get() > .75){
            swerve.xStance();
            return true;
        }
        else {
            return false;
        }
    }
}