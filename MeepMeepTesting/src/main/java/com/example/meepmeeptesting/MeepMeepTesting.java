package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.Constraints;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import org.jetbrains.annotations.NotNull;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(100, 80, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-3, 61, Math.toRadians(270)))


                .lineToY(32)
                //.lineToY(37)
                .setTangent(Math.PI/2)
                        //.waitSeconds(2)
                .splineToConstantHeading(new Vector2d(-26,40),Math.PI)



                //.splineTo(new Vector2d(-48, 48), Math.PI / 2)
                                //.splineTo(new Vector2d(-53,45),3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-44,10),Math.PI)
                        //.lineToX(-48)
                .setTangent(Math.PI/2)
                .lineToY(52)
                .lineToY(10)
                .setTangent(Math.PI)
                .lineToX(-55)
                .setTangent(Math.PI/2)
                .lineToY(52)
                .lineToY(10)
                .setTangent(Math.PI)
                .lineToX(-62)
                .setTangent(Math.PI/2)
                .lineToY(52)



                //.splineToSplineHeading(new Pose2d(-53,45,3*Math.PI/2),3*Math.PI/2)
                                //.strafeToSplineHeading(new Vector2d(-48,42),Math.toRadians(270))
                //.splineTo(new Vector2d(-45,40),3*Math.PI/2)
                //.lineToY(37)
                //.strafeToLinearHeading(new Vector2d(-45,40),Math.PI)
                //.strafeTo(new Vector2d(-55, 45))
                //.lineToYSplineHeading(35,Math.toRadians(180))
                //.lineToXSplineHeading(55,(Math.PI)/2)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setAxesInterval(5)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}