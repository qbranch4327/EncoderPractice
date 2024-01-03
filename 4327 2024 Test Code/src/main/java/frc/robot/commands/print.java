package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class print extends Command{
    String mes;
    
    public print(){
        mes = new String("hello");
    }

    public print(String mes)    {
        this.mes = mes;
    }

    @Override
    public void execute(){
        SmartDashboard.putString("testing", mes);
    }
}
