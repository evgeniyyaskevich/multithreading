package by.epam.javaweb.evgeniyyaskevich.multithreading;

import by.epam.javaweb.evgeniyyaskevich.multithreading.creator.ListTrainFromFileCreator;
import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Train;
import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Tunnel;
import by.epam.javaweb.evgeniyyaskevich.multithreading.exception.IncorrectInputFileException;
import by.epam.javaweb.evgeniyyaskevich.multithreading.generator.TrainFileGenerator;
import by.epam.javaweb.evgeniyyaskevich.multithreading.manager.TunnelManager;
import by.epam.javaweb.evgeniyyaskevich.multithreading.runner.TrainRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.trace("Start working...");
        Tunnel englishTunnel = new Tunnel("English Channel");
        Tunnel minskTunnel = new Tunnel("Minsk Channel");
        TunnelManager englishTunnelManager = new TunnelManager(englishTunnel);
        TunnelManager minskTunnelManager = new TunnelManager(minskTunnel);

        TrainFileGenerator trainFileGenerator = new TrainFileGenerator();
        try {
            trainFileGenerator.generateFileOfTrains(3, "inputTrainsEnglishTunnel");
            trainFileGenerator.generateFileOfTrains(3, "inputTrainsMinskTunnel");

            ListTrainFromFileCreator listTrainFromFileCreator = ListTrainFromFileCreator.getInstance();
            List<Train> englishTrains = listTrainFromFileCreator.getListOfTrains("inputTrainsEnglishTunnel");
            List<Train> minskTrains = listTrainFromFileCreator.getListOfTrains("inputTrainsMinskTunnel");

            for (Train train : englishTrains) {
                new TrainRunner(train, englishTunnelManager).start();
            }

            for (Train train : minskTrains) {
                new TrainRunner(train, minskTunnelManager).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IncorrectInputFileException exception) {
            LOGGER.warn(exception.getMessage());
        }



        /*List<Train> trains = Arrays.asList(new Train(Direction.NORTH),
                                           new Train(Direction.NORTH),
                                           new Train(Direction.NORTH),
                                            new Train(Direction.SOUTH),
                                            new Train(Direction.SOUTH),
                                            new Train(Direction.SOUTH),
                                           new Train(Direction.NORTH),
                                           new Train(Direction.NORTH));

        List<Train> trainsTwo = Arrays.asList(new Train(Direction.NORTH),
                                                new Train(Direction.SOUTH),
                                                new Train(Direction.NORTH));
        for (Train trainTemp : trains) {
           TrainRunner currentRunner =  new TrainRunner(trainTemp, englishTunnelManager);
           currentRunner.start();
        }

        for (Train trainTemp : trainsTwo) {
            TrainRunner currentRunnerTwo = new TrainRunner(trainTemp, tunnelManagerTwo);
            currentRunnerTwo.start();
        }*/
        //TODO: delete this log
        LOGGER.trace("END OF MAIN");
    }
}
