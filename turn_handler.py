def turnHandler(target, robot, turret):  # Inputs: target, robot, and turret headings

    relative = target - robot  # Find target angle relative to the robot

    if relative > 180:  # Handle wrap-around past +180
        relative -= 360
    elif relative < -180:  # Handle wrap-around past -180
        relative += 360

    if relative >= 90:  # Target is outside the safe range on the positive side
        robot_turn = 1  # Robot turns clockwise
        turret_goal = 89.5  # Turret moves toward the safe edge

    elif relative <= -90:  # Target is outside the safe range on the negative side
        robot_turn = -1  # Robot turns counter-clockwise
        turret_goal = -89.5  # Turret moves toward the safe edge

    else:  # Target can be reached by the turret
        robot_turn = 0  # Robot does not move
        turret_goal = relative  # Turret aims directly at target

    error = turret_goal - turret  # Difference between desired and current turret angle

    if turret >= 90:  # Turret must immediately move back into safe range
        turret_turn = -1
    elif turret <= -90:  # Turret must immediately move back into safe range
        turret_turn = 1
    elif -0.5 <= error <= 0.5:  # Turret is close enough to its goal
        turret_turn = 0
    elif error > 0:  # Need to move clockwise
        turret_turn = 1
    else:  # Need to move counter-clockwise
        turret_turn = -1

    return [turret_turn, robot_turn]  # Return both movement directions
