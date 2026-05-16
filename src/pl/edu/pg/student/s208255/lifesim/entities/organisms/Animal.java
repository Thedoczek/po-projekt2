package pl.edu.pg.student.s208255.lifesim.entities.organisms;

import pl.edu.pg.student.s208255.lifesim.controllers.World;
import pl.edu.pg.student.s208255.lifesim.entities.Organism;
import pl.edu.pg.student.s208255.lifesim.structures.Position;

public abstract class Animal extends Organism {
	public Animal(World world, Position position, int strength, int initiative) {
		super(world, position, strength, initiative);
	}

	@Override
	public void action() {
		final Position newPosition = pickNeighbor(1);

		if (position != newPosition) {
			Organism occupant = world.getOccupant(newPosition);

			switch (occupant != null ? occupant.defend(this) : DefendResult.MOVE_ATTACKER) {
				case MOVE_ATTACKER -> {
					world.moveOrganism(this, newPosition);
					position = newPosition;
				}
                case STOP_ATTACKER -> {}
				case KILL_ATTACKER -> alive = false;
				case GIVE_3_STRENGTH -> {
					strength += 3;
					world.moveOrganism(this, newPosition);
					position = newPosition;
				}
			}
		}

		if (alive) {
			age += 1;
		}
	}

	@Override
	public DefendResult defend(Organism attacker) {
		if (attacker.getClass() == this.getClass()) {
			final Position birthPosition = pickEmptyNeighbor(1);
			if (birthPosition != position) {
				world.addSpawn(spawn(position));
				world.writeToLog("Birth at " + birthPosition.getX() + ", " + birthPosition.getY());
			}

			return DefendResult.STOP_ATTACKER;
		}
		world.writeToLog("Fight at" + position.getX() + ", " + position.getY());

		if (attacker.getStrength() > strength || (attacker.getStrength() == strength && attacker.getAge() > age)) {
			alive = false;
			return DefendResult.MOVE_ATTACKER;
		}
		if (attacker.getStrength() == strength && attacker.getAge() == age) {
			return DefendResult.STOP_ATTACKER;
		}

		return DefendResult.KILL_ATTACKER;
	}
}
