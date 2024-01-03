// package frc.robot.subsystems;

// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class BuddyBarSubsystem extends SubsystemBase{
//     private CANSparkMax bar;

//     public BuddyBarSubsystem(){
//         bar = new CANSparkMax(17, MotorType.kBrushless);
//     }

//     public void goUp()  {
//         bar.set(-1);
//     }
    
//     public void goDown(){
//         bar.set(0.5);
//     }

//     public void go(double pwr){
//         bar.set(pwr);
//     }

// }
