package org.firstinspires.ftc.teamcode.powercut.autonomous.redfront;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.powercut.hardware.ArmSystem;
import org.firstinspires.ftc.teamcode.powercut.hardware.DroneSystem;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Config
@Autonomous(name = "FrontRedStageDoorParkCentre", group="RedFront", preselectTeleOp = "Drive")
public class RedFrontStageDoorParkCentre extends OpMode {
    private MecanumDrive drive;
    private ArmSystem arm = new ArmSystem();
    private DroneSystem droneSystem = new DroneSystem();

    // monitoring

    private Action toBackdrop;
    private Action park;
    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, new Pose2d(-36, -63.5, Math.toRadians(90)));
        arm.init(hardwareMap);
        droneSystem.init(hardwareMap);

        telemetry.addLine("Init hardware maps");
        telemetry.update();

        droneSystem.preset();
        arm.doPresetArm();
        arm.doPresetWrist();

        arm.gripLeftActivate();
        arm.gripRightActivate();

        telemetry.addLine("Init hardware positions");
        telemetry.update();



        toBackdrop = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(-57, -40), Math.toRadians(180))
                .strafeTo(new Vector2d(-57,-3))
                .strafeTo(new Vector2d(50, -3))
                .strafeTo(new Vector2d(52, -32))
                .build();

        park = drive.actionBuilder(new Pose2d(52, -36, Math.toRadians(180)))
                .strafeTo(new Vector2d(50, -5))
                .strafeTo(new Vector2d(62, -5))
                .build();


        telemetry.addLine("Init paths. Fully Initialised.");
        telemetry.update();

    }

    @Override
    public void start() {
      Actions.runBlocking(new SequentialAction(
              new SleepAction(1),
              toBackdrop,
              new SleepAction(0.25),
              new ParallelAction(
                      arm.armUp(),
                      arm.wristUp()
              ),
              new SleepAction(0.5),
              arm.gripTuck(),
              new SleepAction(0.5),
              new ParallelAction(arm.presetArm(), arm.presetWrist(), arm.gripRelease(), park)
      ));

        // to backdrop
    }

    @Override
    public void loop() {
        telemetry.addData("Pose", "%4.2f, %4.2f, %4.1f", drive.pose.position.x, drive.pose.position.y, drive.pose.heading.real);
        telemetry.update();
    }
}
