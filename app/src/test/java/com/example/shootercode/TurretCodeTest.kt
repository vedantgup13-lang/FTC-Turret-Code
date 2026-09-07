package com.example.shootercode

import org.junit.Assert.assertArrayEquals
import org.junit.Test

class TurretCodeTest {

    @Test
    fun turretTurnsClockwise() {

        val result = TurretCode.turnHandler(
            targetHeading = 70.0,
            currentRobotHeading = 30.0,
            currentTurretHeading = 10.0
        )

        assertArrayEquals(
            intArrayOf(1, 0),
            result
        )
    }


    @Test
    fun turretTurnsCounterClockwise() {

        val result = TurretCode.turnHandler(
            targetHeading = -50.0,
            currentRobotHeading = 0.0,
            currentTurretHeading = 20.0
        )

        assertArrayEquals(
            intArrayOf(-1, 0),
            result
        )
    }


    @Test
    fun alreadyExactlyAimed() {

        val result = TurretCode.turnHandler(
            targetHeading = 20.0,
            currentRobotHeading = 0.0,
            currentTurretHeading = 20.0
        )

        assertArrayEquals(
            intArrayOf(0, 0),
            result
        )
    }


    @Test
    fun exactlyHalfDegreeIsConsideredAimed() {

        val result = TurretCode.turnHandler(
            targetHeading = 20.0,
            currentRobotHeading = 0.0,
            currentTurretHeading = 19.5
        )

        assertArrayEquals(
            intArrayOf(0, 0),
            result
        )
    }


    @Test
    fun slightlyMoreThanHalfDegreeMustTurn() {

        val result = TurretCode.turnHandler(
            targetHeading = 20.0,
            currentRobotHeading = 0.0,
            currentTurretHeading = 19.49
        )

        assertArrayEquals(
            intArrayOf(1, 0),
            result
        )
    }


    @Test
    fun robotMustTurnClockwise() {

        val result = TurretCode.turnHandler(
            targetHeading = 150.0,
            currentRobotHeading = 20.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(0, 1),
            result
        )
    }


    @Test
    fun robotMustTurnCounterClockwise() {

        val result = TurretCode.turnHandler(
            targetHeading = -150.0,
            currentRobotHeading = -20.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(0, -1),
            result
        )
    }


    @Test
    fun handlesPositiveWrapAround() {

        /*
         * Robot = 170
         * Target = -170
         *
         * Raw difference = -340
         * Normalized difference = +20
         *
         * Target is only 20 degrees clockwise.
         */
        val result = TurretCode.turnHandler(
            targetHeading = -170.0,
            currentRobotHeading = 170.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(1, 0),
            result
        )
    }


    @Test
    fun handlesNegativeWrapAround() {

        /*
         * Robot = -170
         * Target = 170
         *
         * Raw difference = 340
         * Normalized difference = -20
         */
        val result = TurretCode.turnHandler(
            targetHeading = 170.0,
            currentRobotHeading = -170.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(-1, 0),
            result
        )
    }


    @Test
    fun justInsidePositiveTurretLimit() {

        val result = TurretCode.turnHandler(
            targetHeading = 89.999,
            currentRobotHeading = 0.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(1, 0),
            result
        )
    }


    @Test
    fun exactlyPositive90RequiresRobot() {

        val result = TurretCode.turnHandler(
            targetHeading = 90.0,
            currentRobotHeading = 0.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(0, 1),
            result
        )
    }


    @Test
    fun justOutsidePositiveTurretLimit() {

        val result = TurretCode.turnHandler(
            targetHeading = 90.001,
            currentRobotHeading = 0.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(0, 1),
            result
        )
    }


    @Test
    fun justInsideNegativeTurretLimit() {

        val result = TurretCode.turnHandler(
            targetHeading = -89.999,
            currentRobotHeading = 0.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(-1, 0),
            result
        )
    }


    @Test
    fun exactlyNegative90RequiresRobot() {

        val result = TurretCode.turnHandler(
            targetHeading = -90.0,
            currentRobotHeading = 0.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(0, -1),
            result
        )
    }


    @Test
    fun justOutsideNegativeTurretLimit() {

        val result = TurretCode.turnHandler(
            targetHeading = -90.001,
            currentRobotHeading = 0.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(0, -1),
            result
        )
    }


    @Test
    fun targetDirectlyBehindRobot() {

        /*
         * +180 and -180 are equally short.
         *
         * normalizeAngle maps +180 to -180,
         * so this implementation consistently chooses
         * counter-clockwise.
         */
        val result = TurretCode.turnHandler(
            targetHeading = 180.0,
            currentRobotHeading = 0.0,
            currentTurretHeading = 0.0
        )

        assertArrayEquals(
            intArrayOf(0, -1),
            result
        )
    }


    @Test
    fun robotHeadingWrapAroundAndTurretAlreadyAimed() {

        /*
         * Robot = 175
         * Target = -175
         *
         * Target relative to robot = +10 degrees.
         * Turret is already at +10.
         */
        val result = TurretCode.turnHandler(
            targetHeading = -175.0,
            currentRobotHeading = 175.0,
            currentTurretHeading = 10.0
        )

        assertArrayEquals(
            intArrayOf(0, 0),
            result
        )
    }


    @Test
    fun turretAtPositiveBoundaryMovesBackIntoSafeArea() {

        val result = TurretCode.turnHandler(
            targetHeading = 0.0,
            currentRobotHeading = 0.0,
            currentTurretHeading = 90.0
        )

        assertArrayEquals(
            intArrayOf(-1, 0),
            result
        )
    }


    @Test
    fun turretAtNegativeBoundaryMovesBackIntoSafeArea() {

        val result = TurretCode.turnHandler(
            targetHeading = 0.0,
            currentRobotHeading = 0.0,
            currentTurretHeading = -90.0
        )

        assertArrayEquals(
            intArrayOf(1, 0),
            result
        )
    }
}