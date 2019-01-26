package by.epam.javaweb.evgeniyyaskevich.multithreading.manager;

import by.epam.javaweb.evgeniyyaskevich.multithreading.runner.TrainRunner;
import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Direction;
import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Tunnel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class TunnelManager {
    private static final Logger LOGGER = LogManager.getLogger(TunnelManager.class);
    private static final int maxTrainsInTunnel = 3;
    private static final int maxTrainsInOneDirection = 2;
    private final ReentrantLock locker = new ReentrantLock(true);
    private final ReentrantLock lockerSouth = new ReentrantLock(true);
    private final ReentrantLock lockerNorth = new ReentrantLock(true);
    private AtomicInteger amountOfTrainsPassedToNorth = new AtomicInteger(0);
    private AtomicInteger amountOfTrainsPassedToSouth = new AtomicInteger(0);
    //TODO: transfer semaphore in Tunnel Entity
    private Semaphore tunnelSemaphore = new Semaphore(maxTrainsInTunnel);
    private Tunnel tunnel;
    private Direction currentDirection = Direction.SOUTH;

    public TunnelManager(Tunnel tunnel) {
        this.tunnel = tunnel;
    }

    public void requestPermissionForEntryToSouth(TrainRunner trainRunner) {
        trainRunner.printStatus();
        lockerSouth.lock();
        waitNecessaryDirection(trainRunner.getDirection());
        requestPermissionForEntry(trainRunner, amountOfTrainsPassedToSouth, lockerNorth);
    }

    public void requestPermissionForEntryToNorth(TrainRunner trainRunner) {
        trainRunner.printStatus();
        lockerNorth.lock();
        if (lockerSouth.isLocked()) {
            waitNecessaryDirection(trainRunner.getDirection());
        } else {
            changeCurrentDirection();
        }
        requestPermissionForEntry(trainRunner, amountOfTrainsPassedToNorth, lockerSouth);
    }

    private void requestPermissionForEntry(TrainRunner trainRunner,
                                           AtomicInteger passedTrainsCounter, ReentrantLock responsibleLocker) {
        if (passedTrainsCounter.intValue() < maxTrainsInOneDirection) {
            givePermission(trainRunner);
        } else {
            //TODO: rename responsibleLocker!!!
            if (responsibleLocker.isLocked()) {
                waitTunnelRelease();
                changeCurrentDirection();
                waitNecessaryDirection(trainRunner.getDirection());
                passedTrainsCounter.set(0);
                givePermission(trainRunner);
            } else {
                passedTrainsCounter.set(0);
                givePermission(trainRunner);
            }
        }
    }

    private void givePermission(TrainRunner currentTrainRunner) {
        try {
            tunnelSemaphore.acquire();
            LOGGER.trace("Giving permission for " + currentTrainRunner.getTrain() + " in " + tunnel.getName());
            tunnel.allowEntry(currentTrainRunner.getTrain());
            if (currentTrainRunner.getDirection() == Direction.SOUTH) {
                amountOfTrainsPassedToSouth.getAndIncrement();
                amountOfTrainsPassedToNorth.set(0);
                lockerSouth.unlock();
            } else {
                amountOfTrainsPassedToSouth.set(0);
                amountOfTrainsPassedToNorth.getAndIncrement();
                lockerNorth.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releaseTrain(TrainRunner trainRunner) {
        locker.lock();
        try {
            tunnel.release(trainRunner.getTrain());
            trainRunner.printStatus();
            tunnelSemaphore.release();

            Direction trainRunnerDirection = trainRunner.getDirection();
            if (tunnel.isEmpty()) {
                if ((trainRunnerDirection == Direction.NORTH && !lockerNorth.isLocked())
                    || (trainRunnerDirection == Direction.SOUTH && !lockerSouth.isLocked())) {
                    changeCurrentDirection();
                }
            }
        } finally {
            locker.unlock();
        }
    }

    private void waitTunnelRelease() {
        try {
            while (!tunnel.isEmpty()) {
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitNecessaryDirection(Direction necessaryDirection) {
        try {
            while (currentDirection != necessaryDirection) {
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public void setTunnel(Tunnel tunnel) {
        this.tunnel = tunnel;
    }

    private void changeCurrentDirection() {
        locker.lock();
        try {
            currentDirection = (currentDirection == Direction.SOUTH) ? Direction.NORTH : Direction.SOUTH;
        } finally {
            locker.unlock();
        }
    }
}
