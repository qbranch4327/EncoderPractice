// package frc.robot.commands;

// import frc.robot.subsystems.BuddyBarSubsystem;

// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj.Joystick;

// public class BuddyBarCommand extends Command{
//     private BuddyBarSubsystem fork;
//     private Joystick controller;


//     public BuddyBarCommand(BuddyBarSubsystem fork, Joystick controller) {
//         this.fork = fork;
//         this.controller = controller;
//         addRequirements(fork);
//     }

//     @Override
//     public void execute(){
//         if(controller.getRawButton(1)){
//             fork.goDown();
//         }
//         else if(controller.getRawButton(4) && controller.getRawButton(2)){
//             fork.goUp();
//         }
//         else {
//             fork.go(0);
//         }
//     }
// }
