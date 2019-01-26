package by.epam.javaweb.evgeniyyaskevich.multithreading.state;

import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TerminatedTrainState implements TrainState {
    private static final Logger LOGGER = LogManager.getLogger(CreatedTrainState.class);

    private static class SingletonHolder {
        private static final TerminatedTrainState INSTANCE = new TerminatedTrainState();
    }

    private TerminatedTrainState() {}

    public static TerminatedTrainState getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void nextState(Train train) {
        LOGGER.warn("Attempt to get nextState state of the last state.");
    }

    @Override
    public void prevState(Train train) {
        train.setState(MovingTrainState.getInstance());
    }

    @Override
    public String getStateName() {
        return "LEFT";
    }
}
