package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionSubsystem extends SubsystemBase{
    private double x;
    private double y;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;

    public VisionSubsystem() {
    }

    public void publishToDashboard() {
        // read values periodically
        SmartDashboard.putNumber("tx", x);
        SmartDashboard.putNumber("ty", y);
    }

    public void update() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        publishToDashboard();
    }
}
