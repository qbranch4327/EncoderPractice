package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HugSubsystem extends SubsystemBase {
    private final VictorSP hugMotor;

    public HugSubsystem(VictorSP hugMotor) {
        this.hugMotor = hugMotor;
    }

    public void hug() {
        hugMotor.set(-0.5d);
    }

    public void release() {
        hugMotor.set(0.2d);
    }

    public void rest() {
        hugMotor.set(0);
    }
}
