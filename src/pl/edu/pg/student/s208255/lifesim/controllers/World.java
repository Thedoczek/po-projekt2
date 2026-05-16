package pl.edu.pg.student.s208255.lifesim.controllers;

import pl.edu.pg.student.s208255.lifesim.entities.Organism;
import pl.edu.pg.student.s208255.lifesim.entities.organisms.animals.*;
import pl.edu.pg.student.s208255.lifesim.entities.organisms.plants.*;
import pl.edu.pg.student.s208255.lifesim.structures.GlobalRandom;
import pl.edu.pg.student.s208255.lifesim.structures.Position;

import java.util.ArrayList;

public class World {
    private final ArrayList<Organism> queue;
    private final ArrayList<String> log;
    private final Organism[] map;
    private final int sizeX;
    private final int sizeY;

    public World(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.queue = new ArrayList<Organism>();
        this.log = new ArrayList<String>();
        map = new Organism[sizeX*sizeY];
    }

    public void performTurn() {
        // Sort queue by initiative
        queue.sort((a, b) -> {
            if (a.getInitiative() == b.getInitiative()) {
                return Integer.compare(b.getAge(), a.getAge());
            }
            return Integer.compare(b.getInitiative(), a.getInitiative());
        });

        // Perform actions
        final int initialSize = queue.size();
        for (final Organism organism : queue) {
            if (organism.isAlive()) organism.action();
        }

        // Remove dead animals
        for (int i = 0; i < sizeX * sizeY; ++i) {
            if (map[i] != null && !map[i].isAlive()) {
                map[i] = null;
            }
        }
        queue.removeIf(Organism::isAlive);
    }

    public void drawWorld() {
        // To be added
    }

    public Organism getOccupant(Position position) {
        Organism occupant = map[position.getX() * sizeY + position.getY()];
        if (occupant != null && !occupant.isAlive()) {
            return null;
        }

        return occupant;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void addSpawn(Organism spawn) {
        queue.addLast(spawn);
        final Position position = spawn.getPosition();
        map[position.getX() * sizeY + position.getY()] = spawn;
    }

    public void moveOrganism(Organism organism, final Position newPosition) {
        final Position oldPosition = organism.getPosition();
        map[newPosition.getX() * sizeY + newPosition.getY()] = organism;
        map[oldPosition.getX() * sizeY + oldPosition.getY()] = null;
    }

    public void writeToLog(final String s) {
        log.addLast(s);
    }

    public void populateWorld() {
        for (int y = 0; y < sizeY; ++y) {
            for (int x = 0; x < sizeX; ++x) {
                final Position position = new Position(x, y);

                if (get_occupant(position) != null) continue;

                if (GlobalRandom.INSTANCE.nextInt(25) == 0) {
                    switch (GlobalRandom.INSTANCE.nextInt(10)) {
                        case 0 -> addSpawn(new Wolf(this, position));
                        case 1 -> addSpawn(new Sheep(this, position));
                        case 2 -> addSpawn(new Fox(this, position));
                        case 3 -> addSpawn(new Turtle(this, position));
                        case 4 -> addSpawn(new Antelope(this, position));
                        case 5 -> addSpawn(new Grass(this, position));
                        case 6 -> addSpawn(new Dandelion(this, position));
                        case 7 -> addSpawn(new Guarana(this, position));
                        case 8 -> addSpawn(new Belladonna(this, position));
                        case 9 -> addSpawn(new SosnowskyHogweed(this, position));
                        default -> {}
                    }
                }
            }
        }
    }

    public boolean isHumanAlive() {
        for (final Organism organism : queue) {
            if (organism instanceof Human) {
                return organism.isAlive();
            }
        }
        return false;
    }
}
