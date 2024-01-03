package frc.robot;
import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final XboxController driver2 = new XboxController(1);
    private final ElevatorSubsystem eSub;
    private final TiltSubsystem tSub;
    private final IntakeSubsystem iSub;
    private final SpinnerSubsystem sSub;
    // private final BuddyBarSubsystem bSub;

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;
    Command vCommand;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();

    /* Paths */
    private final autoBuilder autoBuilder;
    private static final String[] paths = {
        "Test Auto 2024"
        };

    private final SendableChooser<Command> autoChooser;
    // SendableChooser<String> qChooser = new SendableChooser<>();

    Command autoTest;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        this.eSub = new ElevatorSubsystem();
        this.tSub = new TiltSubsystem();
        this.iSub = new IntakeSubsystem();
        this.sSub = new SpinnerSubsystem();
        // this.bSub = new BuddyBarSubsystem();
        //this.vision = new VisionSubsystem();
        this.autoBuilder = new autoBuilder(s_Swerve, iSub, sSub, eSub);

        // Automatically adds paths from the paths array. The path name at index 0 is set to the default.
        // autoChooser.setDefaultOption(paths[0], paths[0]);
        // for(int i = 1; i < paths.length; i++)  {
        //     autoChooser.addOption(paths[i], paths[i]);
        // }
        
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Mode", autoChooser);

        vCommand = new VomitCommand(iSub, sSub);

        eSub.setDefaultCommand(new ElevatorCommand(eSub, tSub, driver2));
        iSub.setDefaultCommand(new IntakeCommand(iSub, sSub, driver2, driver));
        // bSub.setDefaultCommand(new BuddyBarCommand(bSub, driver));

        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean(),
                driver
            )
        );

        // Configure the button bindings
        configureButtonBindings();
    
        SmartDashboard.putData("Test Auto 2024", new PathPlannerAuto("Test Auto 2024"));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        // zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        SmartDashboard.putData("Test Auto 2024", new PathPlannerAuto("Test Auto 2024"));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        //String selection = autoChooser.getSelected();
        return autoChooser.getSelected();
    }
}
