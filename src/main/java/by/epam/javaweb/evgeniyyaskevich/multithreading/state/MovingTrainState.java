package by.epam.javaweb.evgeniyyaskevich.multithreading.state;

import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Train;

public class MovingTrainState implements TrainState {

    //TODO: Can I write private key for this class and method?
    //TODO: Can I use such singleton implementation?
    private static class SingletonHolder {
        private static final MovingTrainState INSTANCE = new MovingTrainState();
    }

    private MovingTrainState() {}

    public static MovingTrainState getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void nextState(Train train) {
        train.setState(TerminatedTrainState.getInstance());
    }

    @Override
    public void prevState(Train train) {
        train.setState(WaitingTrainState.getInstance());
    }

    @Override
    public String getStateName() {
        return "MOVING";
    }
}
