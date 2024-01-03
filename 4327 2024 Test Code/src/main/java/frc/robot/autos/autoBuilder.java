//From Burdette - I believe this is the 2023 way of making autonomous modes.
//Looking to know if this should be deleted.

package frc.robot.autos;

import java.util.ArrayList;
import java.util.HashMap;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.PIDConstants;
// import com.pathplanner.lib.PathConstraints;
// import com.pathplanner.lib.PathPlanner;
// import com.pathplanner.lib.PathPlannerTrajectory;
// import com.pathplanner.lib.auto.PIDConstants;
// import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class autoBuilder {

    Swerve swerve;
    Subsystem[] subsystems;
    AutoBuilder builder;
    IntakeSubsystem iSub;
    SpinnerSubsystem sSub;
    ElevatorSubsystem eSub;
    VomitCommand vomit;
    AutonIntakeCommand intake;    
    String path;
    HashMap<String, Command> eventMap = new HashMap<>();


    public autoBuilder(Swerve swerve, IntakeSubsystem iSub, SpinnerSubsystem sSub, ElevatorSubsystem eSub) {
        this.swerve = swerve;
        this.iSub = iSub;
        this.sSub = sSub;
        vomit = new VomitCommand(iSub, sSub); 
        intake = new AutonIntakeCommand(iSub, swerve);
        subsystems = new Subsystem[]{swerve};

        // This is just an example event map. It would be better to have a constant, global event map
        // in your code that will be used by all path following commands.
        HashMap<String, Command> eventMap = new HashMap<>();
        eventMap.put("vomitCargo", new VomitCommand(iSub, sSub));
        eventMap.put("elevatorCommand", new AutonElevatorCommand(eSub));
        eventMap.put("dock", new DockCommand(swerve));
        eventMap.put("goOver", new GoOverCommand(swerve));
        eventMap.put("reverseDock", new ReverseDockCommand(swerve));

        // Create the AutoBuilder. This only needs to be created once when robot code starts, not every time you want to create an auto command. A good place to put this is in RobotContainer along with your subsystems.
        // builder = new AutoBuilder(
        //     swerve::getPose, // Pose2d supplier
        //     swerve::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
        //     Constants.Swerve.swerveKinematics, // SwerveDriveKinematics
        //     new PIDConstants(0.05, 0.0, 0.0), // PID constants to correct for translation error (used to create the X and Y PID controllers)
        //     new PIDConstants(0.05, 0.0, 0.0), // PID constants to correct for rotation error (used to create the rotation controller)
        //     swerve::setModuleStates, // Module states consumer used to output to the drive subsystem
        //     eventMap,
        //     true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
        //     swerve // The drive subsystem. Used to properly set the requirements of path following commands
        // );
            
    }
    
    // public Command getAuto(String pathName){
    //     // Consider use of loadPathGroup here
    //     ArrayList<PathPlannerTrajectory> pathGroup = (ArrayList<PathPlannerTrajectory>) PathPlanner.loadPathGroup(pathName, new PathConstraints(4, 3));
    //     Command fullAuto = builder.fullAuto(pathGroup);
    //     return fullAuto;
    // }    
}

