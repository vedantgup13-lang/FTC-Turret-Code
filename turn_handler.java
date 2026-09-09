/*
This function aims the turret at the target while keeping the turret inside its safe -90° to +90° range.
If the turret cannot reach the target by itself, the robot turns until the target becomes reachable.
*/

public class TurretCode {  
    // Creates a class named TurretCode to hold our function.

    public static int[] turnHandler(float target, float robot, float turret) {  
        // Creates the turnHandler function.
        // target = absolute target heading on the field.
        // robot = absolute robot heading on the field.
        // turret = turret angle relative to the robot.
        // int[] means the function returns two integers.

        float relative = (target - robot + 540) % 360 - 180;  
        // Finds where the target is relative to the front of the robot.
        // The extra math keeps the result between -180° and +180°.
        // Example: robot = 170°, target = -170° gives relative = 20° instead of -340°.

        int robotTurn;  
        // Stores which direction the robot should turn:
        // 1 = clockwise, -1 = counter-clockwise, 0 = don't turn.

        float goal;  
        // Stores the angle where we want the turret to move.

        if (relative >= 90) {  
            // Checks if the target is beyond the turret's +90° safe limit.

            robotTurn = 1;  
            // The robot needs to turn clockwise so the target comes into the turret's range.

            goal = 89;  
            // Move the turret toward +89°, just inside the +90° limit.

        } else if (relative <= -90) {  
            // Checks if the target is beyond the turret's -90° safe limit.

            robotTurn = -1;  
            // The robot needs to turn counter-clockwise.

            goal = -89;  
            // Move the turret toward -89°, just inside the -90° limit.

        } else {  
            // Runs when the target is already inside the turret's safe range.

            robotTurn = 0;  
            // The robot does not need to move.

            goal = relative;  
            // The turret can point directly at the target.
        }

        float error = goal - turret;  
        // Finds how far the turret is from where it needs to be.
        // Example: goal = 40°, turret = 10°, error = 30°.

        int turretTurn;  
        // Stores which direction the turret should turn:
        // 1 = clockwise, -1 = counter-clockwise, 0 = stop.

        if (Math.abs(error) <= 0.5) {  
            // Checks whether the turret is within 0.5° of its goal.

            turretTurn = 0;  
            // The turret is close enough, so it stops.

        } else if (error > 0) {  
            // If the error is positive, the turret needs to move in the positive direction.

            turretTurn = 1;  
            // Tell the turret to turn clockwise.

        } else {  
            // If the error is negative, it needs to move in the opposite direction.

            turretTurn = -1;  
            // Tell the turret to turn counter-clockwise.
        }

        return new int[]{turretTurn, robotTurn};  
        // Returns both answers.
        // First number = turret direction.
        // Second number = robot direction.
    }
}
