package by.epam.javaweb.evgeniyyaskevich.multithreading.entity;

import java.util.ArrayList;
import java.util.List;

public class Tunnel {
    private String name;
    private List<Train> movingInsideTrains;

    public Tunnel(String name) {
        this.name = name;
        movingInsideTrains = new ArrayList<>();
    }

    public void allowEntry(Train train) {
        movingInsideTrains.add(train);
    }

    public void release(Train train) {
        movingInsideTrains.remove(train);
    }

    public boolean isEmpty() {
        return movingInsideTrains.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
