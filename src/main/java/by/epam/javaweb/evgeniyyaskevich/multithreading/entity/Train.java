package by.epam.javaweb.evgeniyyaskevich.multithreading.entity;

import by.epam.javaweb.evgeniyyaskevich.multithreading.state.CreatedTrainState;
import by.epam.javaweb.evgeniyyaskevich.multithreading.state.TrainState;

import java.util.concurrent.atomic.AtomicLong;

public class Train {
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private final long id = NEXT_ID.getAndIncrement();
    private TrainState state = CreatedTrainState.getInstance();
    private Direction direction;
    private String name;

    public Train(Direction direction) {
        this.direction = direction;
        this.name = "Train #" + id;
    }

    public Train(String name, Direction direction) {
        this.name = name;
        this.direction = direction;
    }

    public void previousState() {
        state.prevState(this);
    }

    public void nextState() {
        state.nextState(this);
    }

    public TrainState getState() {
        return state;
    }

    public void setState(TrainState state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getName() {
        return name;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Train train = (Train) o;
        return id == train.id &&
                direction == train.direction &&
                name.equals(train.name);
    }

    @Override
    public int hashCode() {
        return 13 + name.hashCode() + Long.hashCode(id);
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", direction=" + direction +
                ", name='" + name + '\'' +
                '}';
    }
}
