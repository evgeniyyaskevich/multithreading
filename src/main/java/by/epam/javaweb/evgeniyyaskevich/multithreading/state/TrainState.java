package by.epam.javaweb.evgeniyyaskevich.multithreading.state;

import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Train;

public interface TrainState  {
    void nextState(Train obj);
    void prevState(Train obj);
    String getStateName();
}
