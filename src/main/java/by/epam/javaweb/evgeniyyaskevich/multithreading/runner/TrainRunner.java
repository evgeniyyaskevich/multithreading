package by.epam.javaweb.evgeniyyaskevich.multithreading.runner;

import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Direction;
import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Train;
import by.epam.javaweb.evgeniyyaskevich.multithreading.manager.TunnelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class TrainRunner extends Thread {
    private static final Logger LOGGER = LogManager.getLogger(TrainRunner.class);
    private Train train;
    private TunnelManager tunnelManager;

    public TrainRunner(Train train, TunnelManager tunnelManager) {
        this.train = train;
        this.tunnelManager = tunnelManager;
    }

    @Override
    public void run() {
        printStatus();

        train.nextState();
        if (train.getDirection() == Direction.SOUTH) {
            tunnelManager.requestPermissionForEntryToSouth(this);
        } else if (train.getDirection() == Direction.NORTH) {
            tunnelManager.requestPermissionForEntryToNorth(this);
        }

        train.nextState();
        printStatus();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        train.nextState();
        tunnelManager.releaseTrain(this);
    }

    public void printStatus() {
        LOGGER.trace(train.getName() +
                " in state: " + train.getState().getStateName() +
                " tunnel: " + tunnelManager.getTunnel());
    }

    public Train getTrain() {
        return train;
    }

    public Direction getDirection() {
        return train.getDirection();
    }

    public TunnelManager getTunnelManager() {
        return tunnelManager;
    }
}
