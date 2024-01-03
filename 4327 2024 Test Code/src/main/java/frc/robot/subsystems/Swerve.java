package frc.robot.subsystems;

import frc.robot.SwerveModule;
import frc.robot.Constants;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

//import com.ctre.phoenix6.hardware.Pigeon2;
//import com.ctre.phoenix6.hardware.core.CorePigeon2;
import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    // private AHRS gyro; 
    private WPI_Pigeon2 gyro; 

    public Swerve() {
        // gyro = new AHRS(I2C.Port.kOnboard); // use w/ I2C
        // gyro = new AHRS(SerialPort.Port.kUSB1);
        gyro = new WPI_Pigeon2(0);
        zeroGyro();

        mSwerveMods = new SwerveModule[] {
            new SwerveModule(0, Constants.Swerve.Mod0.constants),
            new SwerveModule(1, Constants.Swerve.Mod1.constants),
            new SwerveModule(2, Constants.Swerve.Mod2.constants),
            new SwerveModule(3, Constants.Swerve.Mod3.constants)
        };

        AutoBuilder.configureHolonomic(
            this::getPose,
            this::resetOdometry,
            this::getRobotRelativeSpeeds,
            this::driveRobotRelative,
            Constants.Swerve.pathFollowerConfig,
            this
        );

        /* By pausing init for a second before setting module offsets, we avoid a bug with inverting motors.
         * See https://github.com/Team364/BaseFalconSwerve/issues/8 for more info.
         */
        Timer.delay(1.0);
        resetModulesToAbsolute();

        swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getYaw(), getModulePositions());
        
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates =
            Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation)
                                );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }    

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }    

    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getYaw(), getModulePositions(), pose);
    }

    public SwerveModuleState[] getModuleStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : mSwerveMods){
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public ChassisSpeeds getRobotRelativeSpeeds(){
            return Constants.Swerve.swerveKinematics.toChassisSpeeds(getModuleStates());
    }
        
    public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds){
            var states = Constants.Swerve.swerveKinematics.toSwerveModuleStates(robotRelativeSpeeds);
        
            SwerveDriveKinematics.desaturateWheelSpeeds(states, Constants.Swerve.maxSpeed);
        
            setModuleStates(states);
    }
        
    public void driveFieldRelative(ChassisSpeeds fieldRelativeSpeeds){
            ChassisSpeeds robotRelative = ChassisSpeeds.fromFieldRelativeSpeeds(fieldRelativeSpeeds, getPose().getRotation());
        
            driveRobotRelative(robotRelative);
    }

    // Gyro Information //

    public float getRoll() {
        return (float) gyro.getRoll();
    }

    public float getPitch() {
        return (float) gyro.getPitch();
    }

    // public void balance()   {
    //     boolean start = false ;
    //     boolean reverse = false;
    //     if (gyro.getRoll() < -13)    {
    //         slowDown(true);
    //         start = true;
    //     }
    //     else if (gyro.getRoll() > -12 && start && !reverse)    {
    //         xStance();
    //         start = false;
    //     }
    //     if (gyro.getRoll() > 13)    {
    //         slowDown(false);
    //         start = true;
    //         reverse = true;
    //     }
    //     else if (gyro.getRoll() < 12 && start && reverse){
    //         xStance();
    //         start = false;
    //         reverse = false;
    //     }
    // }

    public void zeroGyro(){
        gyro.reset();
    }

    //TODO: We don't know if this works.

    // public void reverseGyro(){
    //     gyro.setAngleAdjustment(180);
    //     gyro.reset();
    // }

    public Rotation2d getYaw() {
        // return (Constants.Swerve.invertGyro) ? Rotation2d.fromDegrees(360 - gyro.getYaw()) : Rotation2d.fromDegrees(gyro.getYaw());
        return gyro.getRotation2d();
    }


    public void resetModulesToAbsolute(){
        for(SwerveModule mod : mSwerveMods){
            mod.resetToAbsolute();
        }
    }

    public void stop()   {
        mSwerveMods[0].setDesiredState(new SwerveModuleState(0, new Rotation2d(Math.PI * 0.75)), false);
        mSwerveMods[1].setDesiredState(new SwerveModuleState(0, new Rotation2d(Math.PI * 0.25)), false);
        mSwerveMods[2].setDesiredState(new SwerveModuleState(0, new Rotation2d(Math.PI * 0.25)), false);
        mSwerveMods[3].setDesiredState(new SwerveModuleState(0, new Rotation2d(Math.PI * 0.75)), false);
    }
    
    public void xStance()   {
        mSwerveMods[0].setDesiredState(new SwerveModuleState(0.1, new Rotation2d(Math.PI * 0.75)), false);
        mSwerveMods[1].setDesiredState(new SwerveModuleState(0.1, new Rotation2d(Math.PI * 0.25)), false);
        mSwerveMods[2].setDesiredState(new SwerveModuleState(0.1, new Rotation2d(Math.PI * 0.25)), false);
        mSwerveMods[3].setDesiredState(new SwerveModuleState(0.1, new Rotation2d(Math.PI * 0.75)), false);
    }

    public void slowDown(boolean direction)  {
        if (direction){
            for (int i = 0; i < mSwerveMods.length; i++)    {
                mSwerveMods[i].setDesiredState(new SwerveModuleState(.4, new Rotation2d(0)), false);
            }
        }
        else {
            for (int i = 0; i < mSwerveMods.length; i++)    {
                mSwerveMods[i].setDesiredState(new SwerveModuleState(-.4, new Rotation2d(0)), false);
            }
        }  
    }

    public void drive(boolean direction)  {
        if (direction){
            for (int i = 0; i < mSwerveMods.length; i++)    {
                mSwerveMods[i].setDesiredState(new SwerveModuleState(2.5, new Rotation2d(0)), false);
            }
        }
        else {
            for (int i = 0; i < mSwerveMods.length; i++)    {
                mSwerveMods[i].setDesiredState(new SwerveModuleState(-2.5, new Rotation2d(0)), false);
            }
        }  
    }

    @Override
    public void periodic(){
        swerveOdometry.update(getYaw(), getModulePositions());  
        // SmartDashboard.putNumber("pitch", gyro.getPitch());
        // SmartDashboard.putNumber("yaw", gyro.getYaw());
        // SmartDashboard.putNumber("roll", gyro.getRoll());

        for(SwerveModule mod : mSwerveMods){
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Integrated", mod.getPosition().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);  
        }

    }


    /* */
}