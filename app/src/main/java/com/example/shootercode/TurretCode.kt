package com.example.shootercode

import kotlin.math.abs

object TurretCode {

    private const val TURRET_LIMIT = 90.0
    private const val AIM_TOLERANCE = 0.5

    /*
     * Coordinate convention used:
     *
     * Positive angle  = clockwise
     * Negative angle  = counter-clockwise
     *
     * Output convention from the assignment:
     *
     *  1 = clockwise
     * -1 = counter-clockwise
     *  0 = no turn
     */

    fun turnHandler(
        targetHeading: Double,
        currentRobotHeading: Double,
        currentTurretHeading: Double
    ): IntArray {

        /*
         * targetHeading and currentRobotHeading are field-centric.
         *
         * Subtracting them converts the target heading into the
         * robot's coordinate system.
         */
        val targetRelative =
            normalizeAngle(targetHeading - currentRobotHeading)

        /*
         * The turret is only allowed strictly between
         * -90 degrees and +90 degrees.
         *
         * If the target is outside this range, the turret cannot
         * reach it safely by itself.
         *
         * Rotate the robot toward the target and keep the turret
         * stationary.
         */
        if (!isInsideTurretSafeZone(targetRelative)) {

            val robotDirection = directionSign(targetRelative)

            return intArrayOf(
                0,                  // turret stays still
                robotDirection      // robot turns
            )
        }

        /*
         * The target is reachable by the turret alone,
         * so the robot does not move.
         */
        val turretError =
            targetRelative - currentTurretHeading

        /*
         * If the turret is already aimed within half a degree,
         * nothing needs to move.
         */
        if (abs(turretError) <= AIM_TOLERANCE) {
            return intArrayOf(0, 0)
        }

        /*
         * Since both the current turret position and desired target
         * position are in the front safe zone, moving directly
         * between them is the shortest path and does not cross
         * the dead zone.
         */
        val turretDirection =
            directionSign(turretError)

        return intArrayOf(
            turretDirection,
            0
        )
    }

    /*
     * Safe turret range is STRICTLY:
     *
     * -90 < angle < +90
     */
    private fun isInsideTurretSafeZone(angle: Double): Boolean {
        return angle > -TURRET_LIMIT &&
                angle < TURRET_LIMIT
    }

    /*
     * Converts an angular error into the required direction output.
     *
     * Positive -> clockwise -> 1
     * Negative -> counter-clockwise -> -1
     * Zero -> no turn -> 0
     */
    private fun directionSign(angle: Double): Int {
        return when {
            angle > 0.0 -> 1
            angle < 0.0 -> -1
            else -> 0
        }
    }

    /*
     * Normalize any angle into:
     *
     * -180 <= angle < 180
     *
     * Examples:
     *
     *  190  -> -170
     * -190  ->  170
     *  360  ->    0
     * -340  ->   20
     */
    private fun normalizeAngle(angle: Double): Double {

        var normalized = angle % 360.0

        if (normalized >= 180.0) {
            normalized -= 360.0
        }

        if (normalized < -180.0) {
            normalized += 360.0
        }

        return normalized
    }
}