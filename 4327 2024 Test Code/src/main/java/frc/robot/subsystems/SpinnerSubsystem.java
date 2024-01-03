package frc.robot.subsystems;

//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;

public class SpinnerSubsystem extends SubsystemBase {

    PWMSparkMax spinnerMotor;
    DigitalInput spinnerStop;

    public SpinnerSubsystem()   {
        spinnerMotor = new PWMSparkMax(3);
        //spinnerStop = new DigitalInput(3);
    }

    public void spin(boolean dir)  {
        if (dir){
            spinnerMotor.set(0.2);
        }
        else{
            spinnerMotor.set(-0.2);
        }
    }

    public void stop(){
        spinnerMotor.stopMotor();
    }
}
