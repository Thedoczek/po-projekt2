package pl.edu.pg.student.s208255.lifesim.entities.organisms.animals;

import pl.edu.pg.student.s208255.lifesim.controllers.World;
import pl.edu.pg.student.s208255.lifesim.entities.Organism;
import pl.edu.pg.student.s208255.lifesim.entities.organisms.Animal;
import pl.edu.pg.student.s208255.lifesim.structures.Position;

public class Antelope extends Animal {
    private static final int STRENGTH = 4;
    private static final int INITIATIVE = 4;

    public Antelope(World world, Position position) {
        super(world, position, STRENGTH, INITIATIVE);
    }

    @Override
    public Organism spawn(final Position position) {
        return new Antelope(world, position);
    }

    @Override
    public void action() {

    }
}
