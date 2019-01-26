package by.epam.javaweb.evgeniyyaskevich.multithreading.state;

import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Train;

public class WaitingTrainState implements TrainState {

    private static class SingletonHolder {
        private static final WaitingTrainState INSTANCE = new WaitingTrainState();
    }

    private WaitingTrainState() {}

    public static WaitingTrainState getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void nextState(Train train) {
        train.setState(MovingTrainState.getInstance());
    }

    @Override
    public void prevState(Train train) {
        train.setState(CreatedTrainState.getInstance());
    }

    @Override
    public String getStateName() {
        return "WAITING IN THE QUEUE";
    }
}
