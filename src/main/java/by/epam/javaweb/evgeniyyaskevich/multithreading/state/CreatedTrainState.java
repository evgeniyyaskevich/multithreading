package by.epam.javaweb.evgeniyyaskevich.multithreading.state;

import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Train;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreatedTrainState implements TrainState {
    private static final Logger LOGGER = LogManager.getLogger(CreatedTrainState.class);

    private static class SingletonHolder {
        private static final CreatedTrainState INSTANCE = new CreatedTrainState();
    }

    private CreatedTrainState() {}

    public static CreatedTrainState getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void nextState(Train train) {
        train.setState(WaitingTrainState.getInstance());
    }

    @Override
    public void prevState(Train train) {
        LOGGER.warn("Attempt to get previous state of root state.");
    }

    @Override
    public String getStateName() {
        return "CREATED";
    }
}
