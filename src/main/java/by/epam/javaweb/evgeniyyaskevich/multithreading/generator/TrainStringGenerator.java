package by.epam.javaweb.evgeniyyaskevich.multithreading.generator;

import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Direction;

import java.util.Random;

public class TrainStringGenerator {
    private Random randomGenerator = new Random();

    private static class SingletonHolder {
        private static final TrainStringGenerator INSTANCE = new TrainStringGenerator();
    }

    public static TrainStringGenerator getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String generateTrainString() {
        Direction currentDirection = (randomGenerator.nextBoolean()) ? Direction.SOUTH : Direction.NORTH;
        return "Train: direction=" + currentDirection;
    }
}
