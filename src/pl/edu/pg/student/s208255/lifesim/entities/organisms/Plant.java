package pl.edu.pg.student.s208255.lifesim.entities.organisms;

import pl.edu.pg.student.s208255.lifesim.controllers.World;
import pl.edu.pg.student.s208255.lifesim.entities.Organism;
import pl.edu.pg.student.s208255.lifesim.structures.GlobalRandom;
import pl.edu.pg.student.s208255.lifesim.structures.Position;

public abstract class Plant extends Organism {
    protected static final int PLANT_SPREAD_FACTOR = 40;
    private static final int INITIATIVE = 0;

    public Plant(World world, Position position, int strength) {
        super(world, position, strength, INITIATIVE);
    }

    @Override
    public void action() {
        if (GlobalRandom.INSTANCE.nextInt(PLANT_SPREAD_FACTOR) == 0) {
            final Position childPosition = pickEmptyNeighbor(1);

            if (childPosition != position) {
                world.addSpawn(spawn(childPosition));
                world.writeToLog("Seed at " + childPosition.getX() + ", " + childPosition.getY());
            }
        }
    }

    @Override
    public DefendResult defend(Organism attacker) {
        alive = false;
        world.writeToLog("Eat at " + position.getX() + ", " + position.getY());
        return DefendResult.MOVE_ATTACKER;
    }
}
