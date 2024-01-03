package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    
    CANSparkMax intakeMotor = new CANSparkMax(16, MotorType.kBrushless);
    DoubleSolenoid intake1;
    DoubleSolenoid intake2;
    DoubleSolenoid grip1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 0);

    public IntakeSubsystem()    {
        intakeMotor.setInverted(true);
    }

    public void intakeOn(boolean forward)  {
        if (forward)    {
            intakeMotor.set(1);
        }
        else {
            intakeMotor.set(-.5);
        }
    }

    
    public void intakeSlow(boolean forward)  {
        if (forward)    {
            intakeMotor.set(.3);
        }
    }

    public void intakeOff() {
        intakeMotor.stopMotor();
    }

    public boolean isCone()     {
        return true;
    }

    public void grab()  {
        grip1.set(DoubleSolenoid.Value.kForward);
    }

    public void release()   {
        grip1.set(DoubleSolenoid.Value.kReverse);
    }

    public void vomit() {
        intakeMotor.set(-1);
    }

    public boolean status(){
        if (grip1.get() == DoubleSolenoid.Value.kForward){
            return true;
        }
        else{
            return false;
        }
    }

}
