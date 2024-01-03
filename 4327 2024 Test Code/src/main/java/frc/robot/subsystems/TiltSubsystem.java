package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TiltSubsystem extends SubsystemBase  {
    
    CANSparkMax tiltMotor;
    DutyCycleEncoder tiltEncoder;
    private final double holdingPwr = -0.085;

    public TiltSubsystem()    {
        tiltMotor = new CANSparkMax(14, MotorType.kBrushless);
        tiltEncoder = new DutyCycleEncoder(2);
    }

    public void tiltDown(double degrees)    {
        if (tiltEncoder.getAbsolutePosition() >= degrees + 0.02)   {
            tiltMotor.set(0.12);
        }
        else if(tiltEncoder.getAbsolutePosition() <= degrees - 0.02){
            tiltMotor.set(-0.12);
        }
        else{
            tiltMotor.set(holdingPwr);
        }
    }

    public void tiltDown(){
        tiltMotor.set(-.1);
    }

    public void tiltUp()  {
        tiltMotor.set(.08);
    }

    public void tiltUp(double degrees)    {
        if (tiltEncoder.getAbsolutePosition() <= degrees - 0.01)   {
            tiltMotor.set(-0.15);
        }
        else{
            tiltMotor.stopMotor();
        }
    }

    public void emergencyTiltUp(double degrees){
        if (tiltEncoder.getAbsolutePosition() >= degrees + 0.01 || tiltEncoder.getAbsolutePosition() <= degrees - 0.01)   {
            tiltMotor.set(-0.45);
        }
        else{
            tiltMotor.stopMotor();
        }
    }

    public boolean encoderCheck(double distance){
        if (tiltEncoder.getAbsolutePosition() == distance)  {
            return true;
        }
        return false;
    }

    @Override
    public void periodic()  {
        SmartDashboard.putNumber("Tilt Encoder", (tiltEncoder.getAbsolutePosition()));
    }

}
