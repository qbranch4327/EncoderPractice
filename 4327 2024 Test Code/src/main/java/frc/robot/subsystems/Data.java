package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Data extends SubsystemBase {

    static ArrayList<Double> numbers = new ArrayList<Double> (){
        {
            add(-8656.75);  //   upperDistance 
            add(-4699.5);  //   middleDistance
            add(0.0);  //   restPosition
            add(0.000034);  //   upperDegree
            add(1.0);  //   middleDegree 
            add(-0.000870);  //   restDegree 
        }
    };

    static ArrayList<String> list = new ArrayList<String> (){
        {
            add("upperDistance"); 
            add("middleDistance"); 
            add("restDistance"); 
            add("upperDegree");
            add("lowerDegree");
            add("restDegree"); 
        }
    };
    

    public static Double n(String str){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals(str)){
                return numbers.get(i);
            }
        }
        return 0.0;
    }
}
