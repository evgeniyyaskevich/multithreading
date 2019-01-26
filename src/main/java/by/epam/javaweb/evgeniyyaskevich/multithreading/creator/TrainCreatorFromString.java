package by.epam.javaweb.evgeniyyaskevich.multithreading.creator;

import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Direction;
import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Train;

public class TrainCreatorFromString {

    private static class SingletonHolder {
        private static final TrainCreatorFromString INSTANCE = new TrainCreatorFromString();
    }

    private TrainCreatorFromString() {}

    public static TrainCreatorFromString getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Train createTrain(String trainString) {
        Direction currentDirection = (trainString.contains("SOUTH"))
                                        ? Direction.SOUTH
                                        : Direction.NORTH;
        return new Train(currentDirection);
    }
}
