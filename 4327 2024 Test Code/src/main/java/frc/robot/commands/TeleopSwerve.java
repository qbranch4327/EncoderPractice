package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;

public class TeleopSwerve extends Command {    
    private Swerve s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;
    private Joystick controller;
    private double speedMultiplier;

    public TeleopSwerve(Swerve s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier robotCentricSup, Joystick controller) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
        this.controller = controller;
    }

    @Override
    public void execute() {
        
        /* Get Values, Deadband*/
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), (Constants.stickDeadband));
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband);
        double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.stickDeadband);

        /* Drive */
        s_Swerve.drive(
            new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed * speedMultiplier), 
            rotationVal * (Constants.Swerve.maxAngularVelocity * speedMultiplier), 
            !robotCentricSup.getAsBoolean(), 
            true
        );

        // if (controller.getRawButton(4)) {
        //     s_Swerve.balance();
        // }

        if (controller.getRawButton(5))   {
            s_Swerve.zeroGyro();
        }

        if (controller.getRawButton(3)) {
            s_Swerve.xStance();
        }

        // Slow down //
        if (controller.getRawAxis(2) > 0.75) {
            speedMultiplier = 0.2;
        }
        else {
            speedMultiplier = 1.0;
        }

        // if (controller.getRawButton(2)){
        //     s_Swerve.reverseGyro();
        // }

    }
}