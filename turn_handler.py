def turnHandler(target, robot, turret):
    relative = target - robot

    if relative > 180:
        relative -= 360
    elif relative < -180:
        relative += 360

    if relative >= 90:
        robot_turn = 1
        turret_goal = 89.5
    elif relative <= -90:
        robot_turn = -1
        turret_goal = -89.5
    else:
        robot_turn = 0
        turret_goal = relative

    error = turret_goal - turret

    if -0.5 <= error <= 0.5:
        turret_turn = 0
    elif error > 0:
        turret_turn = 1
    else:
        turret_turn = -1

    return [turret_turn, robot_turn]