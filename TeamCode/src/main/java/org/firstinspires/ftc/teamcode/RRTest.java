package org.firstinspires.ftc.teamcode;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

//import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(name = "RRTest", group = "Autonomous")
public class RRTest extends LinearOpMode {


   /* public class Claw {
        private Servo claw;

        public Claw(HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "servo0");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.55);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(1.0);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }
    }
*/
    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new SparkFunOTOSDrive(hardwareMap, initialPose);
        //Claw claw = new Claw(hardwareMap);
        Vector2d basketV = new Vector2d(-48, -40);
        int basket_angle = 115;
        // vision here that outputs position
        //int visionOutputPosition = 1;
        TrajectoryActionBuilder tab11, tab12, tab13, tab14, tab15, tab16, tab17, tab18;
        tab11 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(90))
                .lineToY(-35.0); //move to submersible
        tab12 = tab11.endTrajectory().fresh()
                .strafeTo(new Vector2d(-48, -40)); //move to sample 1
        tab13 = tab12.endTrajectory().fresh()
                .strafeToSplineHeading(basketV,Math.toRadians(basket_angle));
        tab14 = tab13.endTrajectory().fresh()
                .strafeToSplineHeading(basketV,Math.toRadians(basket_angle)); //move to basket
        tab15 = tab14.endTrajectory().fresh()
                .turnTo(Math.toRadians(115)); // turn to sample 3
        tab16 = tab15.endTrajectory().fresh()
                .turnTo(Math.toRadians(basket_angle)); // turn to basket
        tab17 = tab16.endTrajectory().fresh()
                .strafeToSplineHeading(new Vector2d(-35, -11), Math.toRadians(0))
                .strafeTo(new Vector2d(-30, -11));
        tab18 = tab17.endTrajectory().fresh()
            .strafeTo(new Vector2d(48, 12));

        // actions that need to happen on init; for instance, a claw tightening.
        //Actions.runBlocking(claw.closeClaw());



        int startPosition = 1;
        telemetry.addData("Starting Position", startPosition);
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;
        if (startPosition == 1) {
            trajectoryActionChosen = tab11.build();
        } else if (startPosition == 2) {
            trajectoryActionChosen = tab11.build();
        } else {
            trajectoryActionChosen = tab11.build();
        }

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen

                        //claw.openClaw(),

                        //trajectoryActionCloseOut
                )
        );
    }
}