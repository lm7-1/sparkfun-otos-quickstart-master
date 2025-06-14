package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting2 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(20, 40, Math.toRadians(180), Math.toRadians(180), 18)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-10, -65, Math.toRadians(90)))

                .setTangent(Math.toRadians(90))
                .lineToY(-35.0) //move to submersible
                .waitSeconds(1)
                //.lineToY(0.0)
                .strafeTo(new Vector2d(-49, -40)) //move to sample
                .waitSeconds(1)
                //.turn(Math.toRadians(-28))
                //.forward(8)
                //.setTangent(Math.toRadians(62))
                                //.forward(10)
                //.strafeTo(new Vector2d(-57, -50), Math.toRadians(0))
                .strafeTo(new Vector2d(-57, -50)) //move to basket
                .turnTo(Math.toRadians(28))
                .waitSeconds(1)
                .strafeTo(new Vector2d(-58, -40)) //move to sample 2

                .waitSeconds(1)
                .strafeTo(new Vector2d(-63, -48)) //move to basket
                //.turn(Math.toRadians(28))
                //.lineToY(-40)
                //.lineToY(37)
                /*.setTangent(Math.PI/2)
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
                */

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setAxesInterval(10)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}