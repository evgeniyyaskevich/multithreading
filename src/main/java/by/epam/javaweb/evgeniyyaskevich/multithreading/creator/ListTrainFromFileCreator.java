package by.epam.javaweb.evgeniyyaskevich.multithreading.creator;

import by.epam.javaweb.evgeniyyaskevich.multithreading.entity.Train;
import by.epam.javaweb.evgeniyyaskevich.multithreading.exception.IncorrectInputFileException;
import by.epam.javaweb.evgeniyyaskevich.multithreading.filereader.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListTrainFromFileCreator {
    private static final Logger LOGGER = LogManager.getLogger(ListTrainFromFileCreator.class);
    private TrainCreatorFromString trainCreatorFromString = TrainCreatorFromString.getInstance();

    private static class SingletonHolder {
        private static ListTrainFromFileCreator INSTANCE = new ListTrainFromFileCreator();
    }

    private ListTrainFromFileCreator() {}

    public static ListTrainFromFileCreator getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Train> getListOfTrains(String fileName) throws IncorrectInputFileException {

        List<Train> listTrains = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(new File(fileName));
            String currentLine;
            while (fileReader.hasNextLine()) {
                currentLine = fileReader.readLine();
                listTrains.add(trainCreatorFromString.createTrain(currentLine));
            }

            return listTrains;
        } catch (IncorrectInputFileException exception) {
            LOGGER.warn("Incorrect file!");
            throw new IncorrectInputFileException("File can`t be opened.", exception);
        }
    }
}
