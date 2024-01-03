package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;

public class ElevatorSubsystem extends SubsystemBase{

    CANSparkMax elevator;
    Encoder elevatorEncoder;
    private final double holdingV = 0.05;
    
    public ElevatorSubsystem()  {
        elevator = new CANSparkMax(15, MotorType.kBrushless);
        elevatorEncoder = new Encoder(0, 1);
        elevator.setInverted(false);
    }

    public void goUp(double distance)  {
        if (elevatorEncoder.getDistance() >= distance + 400)   {
            elevator.set(0.40);
        }
        else if(elevatorEncoder.getDistance() <= distance - 400){
            elevator.set(-0.40);
        }
        else{
            elevator.set(holdingV);
        }
    }
    
    public void goUp()  {
        elevator.set(.3);
    }
    
    public void goDown(){
        elevator.set(-.13);
    }

    public void goDown(double distance)  {
        if (elevatorEncoder.getDistance() <= distance - 200)   {
            elevator.set(-0.13);
        }
        else if (elevatorEncoder.getDistance() >= distance + 200) {
            elevator.set(0.05);
        }
        else  {
           elevator.set(holdingV);
        }
    }

    public void stop()  {
        elevator.stopMotor();
    }

    public void resetEncoders() {
        elevatorEncoder.reset();
    }

    @Override
    public void periodic()  {
        SmartDashboard.putNumber("Elevator Encoder", (elevatorEncoder.getDistance()));
    }

    public boolean encoderCheck(double distance){
        if (elevatorEncoder.getDistance() == distance)  {
            return true;
        }
        return false;
    }

    public double encoderValue(){
        return elevatorEncoder.getDistance();
    }

}
