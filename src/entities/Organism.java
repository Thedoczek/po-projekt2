package entities;

import controllers.World;
import stuctures.GlobalRandom;
import stuctures.Position;
import stuctures.Symbol;

import java.util.ArrayList;

public abstract class Organism {
	protected World world;
	protected Position position;
	protected int strength;
	protected int initiative;
	protected int age = 0;
	protected boolean alive = true;

	Organism(final World world, final Position position, final int strength, final int initiative) {
		this.world = world;
		this.position = position;
		this.strength = strength;
		this.initiative = initiative;
	}

	public abstract Organism spawn(Position pos);
	public abstract void action();
	public abstract DefendResult defend(final Organism attacker);
	public abstract Symbol getSymbol();

	public final Position getPosition() {
		return position;
	};

	public final int getStrength() {
		return strength;
	}

	public final int getInitiative() {
		return initiative;
	}

	public final int getAge() {
		return age;
	}

	public final boolean isAlive() {
		return alive;
	}

	public void kill() {
		alive = false;
	}

	protected final Position pickNeighbor(final int radius) {
		final int minX = Math.max(0, position.getX() - radius);
		final int maxX = Math.min(world.getSizeX() - 1, position.getX() + radius);

		final int minY = Math.max(0, position.getY() - radius);
		final int maxY = Math.min(world.getSizeY() - 1, position.getY() + radius);

		Position newPosition = new Position();

		newPosition.setX(minX + GlobalRandom.INSTANCE.nextInt(maxX - minX + 1));
		newPosition.setY(minY + GlobalRandom.INSTANCE.nextInt(maxY - minY + 1));

		return newPosition;
	}

	protected final Position pickEmptyNeighbor(final int radius) {
		final int minX = Math.max(0, position.getX() - radius);
		final int maxX = Math.min(world.getSizeX() - 1, position.getX() + radius);

		final int minY = Math.max(0, position.getY() - radius);
		final int maxY = Math.min(world.getSizeY() - 1, position.getY() + radius);

		ArrayList<Position> freePositions = new ArrayList<>();

		for (int x = minX; x <= maxX; ++x) {
			for (int y = minY; y <= maxY; ++y) {
				final Position checkedPosition = new Position(x, y);
				if (!world.getOccupant(checkedPosition)) {
					freePositions.add(checkedPosition);
				}
			}
		}

		if (freePositions.isEmpty()) {
			return position;
		}

		return freePositions.get(GlobalRandom.INSTANCE.nextInt(freePositions.size()));
	}

	public enum DefendResult {
		MOVE_ATTACKER,
		STOP_ATTACKER,
		KILL_ATTACKER,
		GIVE_3_STRENGTH
	}
}
