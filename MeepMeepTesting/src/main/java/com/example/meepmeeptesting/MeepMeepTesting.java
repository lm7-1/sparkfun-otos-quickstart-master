package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-3, 61, Math.toRadians(270)))


                .lineToY(32)
                .lineToY(37)
                .splineTo(new Vector2d(-48, 48), Math.PI / 2)
                                //.splineTo(new Vector2d(-53,45),3*Math.PI/2)
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