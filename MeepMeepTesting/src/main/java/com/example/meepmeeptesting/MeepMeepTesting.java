package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

//import static org.firstinspires.ftc.teamcode.FieldLocations.*;


public class MeepMeepTesting {

    public static boolean left_Start = false;
    public static double[] sample1_L = {-48,-49};
    public static double[] sample1_R = {45,-12};
    public static double[] sample2_L = {sample1_L[0] - 10,sample1_L[1]};
    public static double[] sample2_R = {55,-12};
    public static double[] sample3_L = {60,-12};
    public static double[] sample3_R = {65,-12};

    public static double[] get_specimen = {48,-60};
    public static double[] initial_L = {-10,-61};
    public static double[] initial_R = {14,-61};
    public static double[] sub_clip_L = {-10,-45};
    public static double[] sub_clip_R = {14,-44};

    public static double[] basket_vect = {-54,-50};

    public static double sample_push_y = -53;

    public static int CLIP_ARM_POS = 250;
    public static int CLIP_VIPER_POS = 250;

    public static double basket_ang = 45;
    public static double[] parking_pose1 = {-38, -11};

    public static double[] parking_pose2 = {-30, -11};

    public static double[] sample3_angle = {128,.4};
    public static double[] sample2_angle = {112,.4};

    public static double[] sample1_angle = {73,.3};

    public static int BASKET_VIPER_POS = 725;
    public static int BASKET_ARM_POS = 800;
    public static int ARM_STORED = 15;
    public static int VIPER_STORED = 50;
    public static int ARM_YOINK = -75;

    public static int VIPER_YOINK = 300;
    public static int ARM_YOINK_PREP = 50;
    public static int ARM_VEL_SLOW = 750;
    public static int ARM_VEL_FAST = 1200;
    public static int VIPER_VEL_SLOW = 600;
    public static int VIPER_VEL_FAST = 2000;
    public static int slow_bot_vel = 5;
    public static double grabClose = .6;
    public static double grabOpen = .2;
    public static double twistPar = 0;
    public static double twistPerp = .5;

    public static double wrist_yoink = .85;

    public static double wrist_drop = .15;

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        Vector2d basketV = new Vector2d(-57,-50); // vector for basket
        int basket_angle = 60; //angle to face basket

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 40, Math.toRadians(180), Math.toRadians(180), 18)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(initial_R[0], initial_R[1], Math.toRadians(90)))

                /*
                .strafeTo(new Vector2d(sub_clip_R[0], sub_clip_R[1])) //move to hang pre-loaded specimen //, new TranslationalVelConstraint(slow_bot_vel));
                .strafeToLinearHeading(new Vector2d(sub_clip_R[0] + 22, sub_clip_R[1] ),Math.toRadians(-90))
                .strafeTo(new Vector2d(sub_clip_R[0] + 22, sub_clip_R[1] + 22) )
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(sample1_R[0], sample1_R[1]),Math.toRadians(-90)) //move to sample 1
                .strafeTo(new Vector2d(sample1_R[0], sample_push_y)) //push sample to wall
                .strafeToLinearHeading(new Vector2d(sub_clip_R[0] - 2, sub_clip_R[1]),Math.toRadians(90)) //hang specimen 1
                .strafeToLinearHeading(new Vector2d(sub_clip_R[0] + 12, sub_clip_R[1]),Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(sample2_R[0], sample2_R[1]), Math.toRadians(-90)) //move to sample 2
                .strafeTo(new Vector2d(sample2_R[0], sample_push_y))//push sample 2 to wall
                .strafeTo(new Vector2d(sample1_R[0], sample_push_y))//get specimen
                .strafeToLinearHeading(new Vector2d(sub_clip_R[0] - 4, sub_clip_R[1]),Math.toRadians(90)) //hang specimen 2
                .strafeToLinearHeading(new Vector2d(sub_clip_R[0] + 15, sub_clip_R[1]),Math.toRadians(-90))
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(sample3_R[0], sample3_R[1]), Math.toRadians(-90)) //move to sample 3
                .strafeTo(new Vector2d(sample3_R[0], sample_push_y))//push sample 3 to wall
                .strafeTo(new Vector2d(sample1_R[0], sample_push_y))//get specimen
                .setTangent(Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(sub_clip_R[0] - 6, sub_clip_R[1]),Math.toRadians(90)) //hang specimen 3
                .setTangent(Math.toRadians(0))
                .strafeTo(new Vector2d(sample2_R[0]-17, sample_push_y -7) )

                 */
                //traj1 = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(basket_vect[0],basket_vect[1]), Math.toRadians(basket_ang))//, new TranslationalVelConstraint(slow_bot_vel));
        //traj2 = traj1.endTrajectory().fresh()
                .turnTo(Math.toRadians(sample1_angle[0]))
                //.strafeToLinearHeading(new Vector2d(sample1_L[0],sample1_L[1]),Math.toRadians(90))
        //traj3 = traj2.endTrajectory().fresh()
                .turnTo(Math.toRadians(basket_ang))
                //.strafeToLinearHeading(new Vector2d(basket_vect[0],basket_vect[1]), Math.toRadians(90))
        //traj4 = traj3.endTrajectory().fresh()
                .turnTo(Math.toRadians(sample2_angle[0]))
                //.strafeToLinearHeading(new Vector2d(sample2_L[0],sample2_L[1]), Math.toRadians(90))
        //traj5 = traj4.endTrajectory().fresh()
                .turnTo(Math.toRadians(basket_ang))
                //.strafeToLinearHeading(new Vector2d(basket_vect[0],basket_vect[1]), Math.toRadians(90))
        //traj6 = traj5.endTrajectory().fresh()
                .turnTo(Math.toRadians(sample3_angle[0]))
        //traj7 = traj6.endTrajectory().fresh()
                .turnTo(Math.toRadians(basket_ang))
        //traj8 = traj7.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(parking_pose1[0],parking_pose1[1]), Math.toRadians(0))

        //traj9 = traj8.endTrajectory().fresh()
                .strafeTo(new Vector2d(parking_pose2[0],parking_pose2[1]))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setAxesInterval(10)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}