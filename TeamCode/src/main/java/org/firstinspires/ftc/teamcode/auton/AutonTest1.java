package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.SleepAction;
import static org.firstinspires.ftc.teamcode.FieldLocations.*;
import com.acmerobotics.roadrunner.Action;


import org.firstinspires.ftc.teamcode.PinpointDrive;

import org.firstinspires.ftc.teamcode.auton.subsystems.*;






@Autonomous(name = "AutonTest1", group = "Autonomous")
public class AutonTest1 extends LinearOpMode {

        public void runOpMode() {
        MyArm myarm = new MyArm(hardwareMap);
        Pose2d initialPose;
        if (left_Start) {
            initialPose = new Pose2d(initial_L[0], initial_L[1], Math.toRadians(90));
        } else {
            initialPose = new Pose2d(initial_R[0], initial_R[1], Math.toRadians(90));
        }

        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);

        myarm.initArm();
        myarm.initViper();

        Action DROP_ACTION = new SequentialAction(

                myarm.moveArmTo(BASKET_ARM_POS, ARM_VEL_FAST),
                myarm.moveViperTo(BASKET_VIPER_POS, VIPER_VEL_FAST),
                myarm.wristTo(wrist_drop),
                //new SleepAction(servo_delay),
                myarm.grabTo(grabOpen),
                //new SleepAction(servo_delay),
                myarm.wristTo(wrist_yoink),
                //new SleepAction(servo_delay),
                myarm.moveViperTo(VIPER_STORED, VIPER_VEL_SLOW),
                myarm.moveArmTo(ARM_STORED, ARM_VEL_FAST)
        );

        Action YOINK_ACTION_PREP = new SequentialAction(

                    myarm.grabTo(grabOpen),
                    //new SleepAction(servo_delay),
                    //myarm.twistTo(twistPar)
                    //new SleepAction(servo_delay)
                    myarm.moveArmTo(ARM_YOINK, ARM_VEL_SLOW)


        );
        Action YOINK_ACTION = new SequentialAction(


                    //myarm.moveArmTo(ARM_YOINK, ARM_VEL_SLOW),
                    myarm.grabTo(grabClose),
                    //new SleepAction(servo_delay),
                    //myarm.moveArmTo(ARM_YOINK_PREP, ARM_VEL_FAST),
                    myarm.moveArmTo(ARM_STORED, ARM_VEL_FAST),
                    myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST)

        );


        // Wait until start and set up parameters
        while (!opModeIsActive() && !isStopRequested()) {
            if (gamepad2.left_trigger > 0.25) {
                left_Start = true;
            }
            if (gamepad2.right_trigger > 0.25) {
                left_Start = true;
            }

            telemetry.addLine("-------Initialized-------");
            telemetry.addLine("Left = " + left_Start);
            telemetry.addLine(">>>> Press ▶ to start. <<<<");
            telemetry.update();
        }

        Vector2d SUB_CLIP_L = new Vector2d(sub_clip_L[0], sub_clip_L[1]);
        Vector2d SUB_CLIP_R = new Vector2d(sub_clip_R[0], sub_clip_R[1]);
        Vector2d FIRST_SAMPLE_L = new Vector2d(sample1_L[0], sample1_L[1]);
        Vector2d FIRST_SAMPLE_R = new Vector2d(sample1_R[0], sample1_R[1]);
        Vector2d BASKET_VECT = new Vector2d(basket_vect[0],basket_vect[1]);
        Vector2d SECOND_SAMPLE_L = new Vector2d(sample2_L[0], sample2_L[1]);
        Vector2d PARKING_POSE_2 = new Vector2d(parking_pose2[0],parking_pose2[1]);
        Vector2d PARKING_POSE_1 = new Vector2d(parking_pose1[0],parking_pose1[1]);





        TrajectoryActionBuilder traj1, traj2, traj3, traj4, traj5, traj6, traj7, traj8, traj9, traj10, traj11, traj12, traj13, traj14;

        if (!left_Start) { //right side
            traj1 = drive.actionBuilder(initialPose)
            .strafeTo(new Vector2d(sub_clip_R[0], sub_clip_R[1])); //move to hang pre-loaded specimen //, new TranslationalVelConstraint(slow_bot_vel));
            traj2 = traj1.endTrajectory().fresh()
                    .strafeToLinearHeading(new Vector2d(sub_clip_R[0] + 22, sub_clip_R[1] ),Math.toRadians(-90))
                    .strafeTo(new Vector2d(sub_clip_R[0] + 22, sub_clip_R[1] + 22) )
                    .setTangent(Math.toRadians(90))
                    .splineToConstantHeading(new Vector2d(sample1_R[0], sample1_R[1]),Math.toRadians(-90)) //move to sample 1
                    .strafeTo(new Vector2d(sample1_R[0], sample_push_y)) //push sample to wall
                    .strafeToLinearHeading(new Vector2d(sub_clip_R[0] - 2, sub_clip_R[1]),Math.toRadians(90)); //hang specimen 1
            traj3 = traj2.endTrajectory().fresh()
                    .strafeToLinearHeading(new Vector2d(sub_clip_R[0] + 12, sub_clip_R[1]),Math.toRadians(-90))
                    .splineToConstantHeading(new Vector2d(sample2_R[0], sample2_R[1]), Math.toRadians(-90)) //move to sample 2
                    .strafeTo(new Vector2d(sample2_R[0], sample_push_y))//push sample 2 to wall
                    .strafeTo(new Vector2d(sample1_R[0], sample_push_y));//get specimen
            traj4 = traj3.endTrajectory().fresh()
                    .strafeToLinearHeading(new Vector2d(sub_clip_R[0] - 4, sub_clip_R[1]),Math.toRadians(90)); //hang specimen 2
            traj5 = traj4.endTrajectory().fresh()
                    .strafeToLinearHeading(new Vector2d(sub_clip_R[0] + 15, sub_clip_R[1]),Math.toRadians(-90))
                    .setTangent(Math.toRadians(0))
                    .splineToConstantHeading(new Vector2d(sample3_R[0], sample3_R[1]), Math.toRadians(-90)) //move to sample 3
                    .strafeTo(new Vector2d(sample3_R[0], sample_push_y))//push sample 3 to wall
                    .strafeTo(new Vector2d(sample1_R[0], sample_push_y));//get specimen
            traj6 = traj5.endTrajectory().fresh()
                    .setTangent(Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(sub_clip_R[0] - 6, sub_clip_R[1]),Math.toRadians(90)); //hang specimen 3
            traj7 = traj6.endTrajectory().fresh()
                    .setTangent(Math.toRadians(0))
                    .strafeTo(new Vector2d(sample2_R[0]-17, sample_push_y -7) );
/*
            traj1 = drive.actionBuilder(initialPose).strafeTo(new Vector2d(sub_clip_R[0], sub_clip_R[1])); //move to hang pre-loaded specimen //, new TranslationalVelConstraint(slow_bot_vel));
            traj2 = traj1.endTrajectory().fresh().setTangent(Math.toRadians(90))
                    .strafeTo(new Vector2d(sample1_R[0], sample1_R[1])); //move to sample 1
            traj3 = traj2.endTrajectory().fresh()
                    .strafeTo(new Vector2d(sample1_R[0], sample_push_y)); //push sample to wall
            traj4 = traj3.endTrajectory().fresh().setTangent(Math.toRadians(270))
                    .strafeTo(new Vector2d(get_specimen[0], get_specimen[1])); //get specimen from wall
                    //.strafeToLinearHeading(SECOND_SAMPLE_L, Math.toRadians(90));
            traj5 = traj4.endTrajectory().fresh().setTangent(Math.toRadians(90))
                    .strafeTo(new Vector2d(sub_clip_R[0] + 2, sub_clip_R[1])); //hang specimen 1
            traj6 = traj5.endTrajectory().fresh()
                    .splineToConstantHeading(new Vector2d(sample2_R[0], sample2_R[1]),Math.toRadians(90) ); //move to sample 2
            //.turnTo(THIRD_SAMPLE_ANGLE);
            traj7 = traj6.endTrajectory().fresh()
                    .strafeTo(new Vector2d(sample2_R[0], sample_push_y));//push sample 2 to wall
                    //.turnTo(BASKET_ANGLE);
            traj8 = traj7.endTrajectory().fresh().setTangent(Math.toRadians(270))
                    .strafeTo(new Vector2d(get_specimen[0], get_specimen[1])); //get specimen
            traj9 = traj8.endTrajectory().fresh().setTangent(Math.toRadians(90))
                    .strafeTo(new Vector2d(sub_clip_R[0] + 4, sub_clip_R[1])); //hang specimen 2
            traj10 = traj9.endTrajectory().fresh()
                    .strafeTo(new Vector2d(sample3_R[0], sample3_R[1])); //move to sample

            traj11 = traj10.endTrajectory().fresh()
                    .strafeTo(new Vector2d(sample3_R[0], sample_push_y));//push sample 2 to wall

            traj12 = traj11.endTrajectory().fresh().setTangent(Math.toRadians(270))
                    .strafeTo(new Vector2d(get_specimen[0], get_specimen[1])); //get specimen
            traj13 = traj12.endTrajectory().fresh().setTangent(Math.toRadians(90))
                    .strafeTo(new Vector2d(sub_clip_R[0] + 6, sub_clip_R[1])); //hang specimen 3
            traj14 = traj13.endTrajectory().fresh()
                    .strafeTo(new Vector2d(sample2_R[0], sample_push_y));//finish in end zom
*/
            waitForStart();
            Actions.runBlocking( //right side

                    new SequentialAction(
                            // Clip preload specimen
                            DROP_ACTION,
                            new ParallelAction(

                                myarm.grabTo(grabClose),
                                myarm.twistTo(.18),
                                //myarm.moveArmTo(CLIP_ARM_POS, ARM_VEL_FAST),
                                new SleepAction(1),
                                //myarm.moveViperTo(CLIP_VIPER_POS, VIPER_VEL_FAST)
                                traj1.build()//move to sub to hang specimen
                            ),
                            new ParallelAction (
                                traj2.build()
                                //myarm.moveArmTo(ARM_STORED, ARM_VEL_FAST),
                                //myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST),
                            ),
                            new ParallelAction (
                                traj3.build()
                            ),
                            new ParallelAction (
                                    traj4.build()
                            ),
                            new ParallelAction (
                                    traj5.build()
                            ),
                            new ParallelAction (
                                    traj6.build()
                            ),
                            new ParallelAction (
                                    traj7.build()
                            )

                    )
            );
        } else {  //left side
            traj1 = drive.actionBuilder(initialPose)
                    .strafeToLinearHeading(new Vector2d(basket_vect[0],basket_vect[1]), Math.toRadians(basket_ang));
            traj2 = traj1.endTrajectory().fresh()
                    .turnTo(Math.toRadians(sample1_angle[0]));
            traj3 = traj2.endTrajectory().fresh()
                    .turnTo(Math.toRadians(basket_ang));
            traj4 = traj3.endTrajectory().fresh()
                    .turnTo(Math.toRadians(sample2_angle[0]));
                    //.strafeToLinearHeading(new Vector2d(sample2_L[0],sample2_L[1]), Math.toRadians(90))
            traj5 = traj4.endTrajectory().fresh()
                    .turnTo(Math.toRadians(basket_ang));
                    //.strafeToLinearHeading(new Vector2d(basket_vect[0],basket_vect[1]), Math.toRadians(90))
            traj6 = traj5.endTrajectory().fresh()
                    .turnTo(Math.toRadians(sample3_angle[0]));
            traj7 = traj6.endTrajectory().fresh()
                    .turnTo(Math.toRadians(basket_ang));
            traj8 = traj7.endTrajectory().fresh()
                    .strafeToLinearHeading(new Vector2d(parking_pose1[0],parking_pose1[1]), Math.toRadians(0));

            traj9 = traj8.endTrajectory().fresh()
                    .strafeTo(new Vector2d(parking_pose2[0],parking_pose2[1]));

            Actions.runBlocking( //left side

                    new SequentialAction(
                            // Clip preload specimen





                            //drop pre loaded sample
                            new ParallelAction (
                                    myarm.grabTo(grabClose),
                                    myarm.twistTo(twistPar),
                                    new SequentialAction(
                                            new SleepAction(.25),
                                            traj1.build()//move to basket
                                    ),
                                    new SequentialAction(
                                        new SleepAction(.25),
                                        myarm.moveArmTo(BASKET_ARM_POS, ARM_VEL_FAST),
                                        myarm.moveViperTo(BASKET_VIPER_POS, VIPER_VEL_FAST)
                                    )
                            ),
                            new SequentialAction(
                                myarm.wristTo(wrist_drop),
                                myarm.grabTo(grabOpen)
                            ),

                            new ParallelAction (
                                    myarm.wristTo(wrist_yoink),
                                    new SequentialAction(
                                        myarm.moveViperTo(VIPER_STORED, VIPER_VEL_SLOW),
                                        //new SleepAction(.5),
                                        myarm.moveArmTo(ARM_STORED, ARM_VEL_SLOW)
                                    ),
                                    traj2.build()//turn to sample 1
                            ),
                            new ParallelAction (
                                    //myarm.moveArmTo(ARM_YOINK_PREP, ARM_VEL_FAST),
                                    myarm.moveViperTo(VIPER_YOINK, VIPER_VEL_FAST)
                            ),
                            new SequentialAction(
                                    new SleepAction(.1),
                                    myarm.armOff(),
                                    //myarm.moveArmTo(ARM_YOINK, ARM_VEL_SLOW), //move arm to proper yoink position
                                    new SleepAction(.8),
                                    myarm.grabTo(grabClose)
                            ),
                            new ParallelAction (
                                    myarm.moveArmTo(ARM_STORED, ARM_VEL_FAST),
                                    myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST),
                                    traj3.build() //turn to basket
                            ),

                            new SequentialAction(
                                    myarm.moveArmTo(BASKET_ARM_POS, ARM_VEL_FAST),
                                    //new SleepAction(.5),
                                    myarm.moveViperTo(BASKET_VIPER_POS, VIPER_VEL_FAST),
                                    myarm.wristTo(wrist_drop),
                                    myarm.grabTo(grabOpen)
                            ),
                            new ParallelAction (
                                    myarm.wristTo(wrist_yoink),
                                    new SequentialAction(
                                            myarm.moveViperTo(VIPER_STORED, VIPER_VEL_SLOW),
                                            //new SleepAction(.5),
                                            myarm.moveArmTo(ARM_STORED, ARM_VEL_SLOW)
                                    ),
                                    traj4.build()//turn to sample 2
                            ),
                            new ParallelAction (
                                    //myarm.moveArmTo(ARM_YOINK_PREP, ARM_VEL_FAST),
                                    myarm.moveViperTo(VIPER_YOINK2, VIPER_VEL_FAST)
                            ),
                            new SequentialAction(
                                    new SleepAction(.1),
                                    myarm.armOff(),
                                    //myarm.moveArmTo(ARM_YOINK2, ARM_VEL_SLOW), //move arm to proper yoink position
                                    new SleepAction(.8),
                                    myarm.grabTo(grabClose)
                            ),
                            new ParallelAction (
                                    myarm.moveArmTo(ARM_STORED, ARM_VEL_FAST),
                                    myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST),
                                    traj5.build() //turn to basket
                            ),

                            new SequentialAction(
                                    myarm.moveArmTo(BASKET_ARM_POS, ARM_VEL_FAST),
                                    //new SleepAction(.5),
                                    myarm.moveViperTo(BASKET_VIPER_POS, VIPER_VEL_FAST),
                                    myarm.wristTo(wrist_drop),
                                    myarm.grabTo(grabOpen)
                            ),
                            new ParallelAction (
                                myarm.wristTo(wrist_yoink),
                            new SequentialAction(
                                    myarm.moveViperTo(VIPER_STORED, VIPER_VEL_SLOW),
                                    //new SleepAction(1.5),
                                    myarm.moveArmTo(ARM_STORED, ARM_VEL_SLOW)
                                    //new SleepAction(1)

                            ),
                            traj6.build()//turn to sample 3
                    ),
                            new ParallelAction (
                                    //myarm.moveArmTo(ARM_YOINK_PREP, ARM_VEL_FAST),
                                    myarm.twistTo(twist45),
                                    myarm.moveViperTo(VIPER_YOINK3, VIPER_VEL_FAST)
                            ),
                            new SequentialAction(
                                    new SleepAction(.3),
                                    myarm.armOff(),
                                    //myarm.moveArmTo(ARM_YOINK3, ARM_VEL_SLOW), //move arm to proper yoink position
                                    new SleepAction(.8),
                                    myarm.grabTo(grabClose)
                            ),
                            new ParallelAction (
                                    myarm.moveArmTo(ARM_STORED, ARM_VEL_FAST),
                                    myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST),
                                    traj7.build() //turn to basket
                            ),

                            new SequentialAction(
                                    myarm.moveArmTo(BASKET_ARM_POS, ARM_VEL_FAST),
                                    //new SleepAction(.5),
                                    myarm.moveViperTo(BASKET_VIPER_POS, VIPER_VEL_FAST),
                                    myarm.wristTo(wrist_drop),
                                    new SleepAction(.3),
                                    myarm.grabTo(grabOpen)
                            ),
                            new ParallelAction (
                                    myarm.wristTo(wrist_yoink),
                                    new SequentialAction(
                                            myarm.moveViperTo(VIPER_STORED, VIPER_VEL_SLOW),
                                            new SleepAction(.8),
                                            myarm.moveArmTo(ARM_STORED, ARM_VEL_SLOW),
                                            new SleepAction(.6)
                                    )
                            ),

                            new ParallelAction (
                                    traj8.build(), //prepare for park
                                    myarm.moveArmTo(0, ARM_VEL_SLOW),
                                    myarm.moveViperTo(0, VIPER_VEL_SLOW)
                            ),
                            traj9.build()


/*
                            //get sample 1
                            myarm.moveArmTo(ARM_YOINK_PREP, ARM_VEL_FAST),
                            myarm.moveViperTo(VIPER_YOINK, VIPER_VEL_FAST),

                            myarm.moveArmTo(ARM_YOINK, ARM_VEL_SLOW), //move arm to proper yoink position

                            myarm.grabTo(grabClose),
                            myarm.moveArmTo(ARM_STORED, ARM_VEL_FAST),
                            myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST),

                            traj3.build(), //turn to basket

                            //drop sample 1
                            myarm.moveArmTo(BASKET_ARM_POS, ARM_VEL_FAST),
                            myarm.moveViperTo(BASKET_VIPER_POS, VIPER_VEL_FAST),
                            myarm.wristTo(wrist_drop),
                            myarm.grabTo(grabOpen),
                            myarm.wristTo(wrist_yoink),
                            myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST),
                            //new SleepAction(.25),
                            myarm.moveArmTo(ARM_STORED, ARM_VEL_SLOW),


                            traj4.build(), //turn to sample 2

                            //get sample 2
                            myarm.moveArmTo(ARM_YOINK_PREP, ARM_VEL_FAST),
                            myarm.moveViperTo(VIPER_YOINK2, VIPER_VEL_FAST),
                            //new SleepAction(servo_delay),
                            myarm.moveArmTo(ARM_YOINK2, ARM_VEL_SLOW), //move arm to proper yoink position
                            //new SleepAction(.25),
                            myarm.grabTo(grabClose),
                            myarm.moveArmTo(ARM_STORED, ARM_VEL_FAST),
                            myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST),

                            traj5.build(), //turn to basket

                            //drop sample 2
                            myarm.moveArmTo(BASKET_ARM_POS, ARM_VEL_FAST),
                            myarm.moveViperTo(BASKET_VIPER_POS, VIPER_VEL_FAST),
                            myarm.wristTo(wrist_drop),
                            myarm.grabTo(grabOpen),
                            myarm.wristTo(wrist_yoink),
                            myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST),
                            //new SleepAction(.25),
                            myarm.moveArmTo(ARM_STORED, ARM_VEL_SLOW),

                            traj6.build(), //turn to sample 3

                            //get sample 3
                            myarm.moveArmTo(ARM_YOINK_PREP, ARM_VEL_FAST),
                            myarm.moveViperTo(VIPER_YOINK3, VIPER_VEL_FAST),
                            new SleepAction(servo_delay),
                            myarm.moveArmTo(ARM_YOINK3, ARM_VEL_SLOW), //move arm to proper yoink position
                            //new SleepAction(.25),
                            myarm.grabTo(grabClose),
                            myarm.moveArmTo(ARM_STORED, ARM_VEL_FAST),
                            myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST),

                            traj7.build(), //turn to basket

                            //drop sample 3
                            myarm.moveArmTo(BASKET_ARM_POS, ARM_VEL_FAST),
                            myarm.moveViperTo(BASKET_VIPER_POS, VIPER_VEL_FAST),
                            myarm.wristTo(wrist_drop),
                            myarm.grabTo(grabOpen),
                            myarm.wristTo(wrist_yoink),
                            myarm.moveViperTo(VIPER_STORED, VIPER_VEL_FAST),
                            //new SleepAction(.25),
                            myarm.moveArmTo(ARM_STORED, ARM_VEL_SLOW),

                            traj8.build(), //prepare for park
                            //myarm.moveArmTo(ARM_PARK, ARM_VEL_FAST),
                            //myarm.moveViperTo(VIPER_PARK, VIPER_VEL_FAST),
                            traj9.build() // park
                            //myarm.armOff()
*/

                    )
            );
        }


        telemetry.addData("Path", "Execution complete");
        telemetry.update();

    }
}

