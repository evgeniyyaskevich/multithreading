package by.epam.javaweb.evgeniyyaskevich.multithreading.generator;

import java.io.FileWriter;
import java.io.IOException;

public class TrainFileGenerator {
    private TrainStringGenerator trainStringGenerator = TrainStringGenerator.getInstance();

    public void generateFileOfTrains(int amount, String nameFile) throws IOException {
        FileWriter fileWriter = new FileWriter(nameFile);
        for (int i = 0; i < amount; ++i) {
            fileWriter.write(trainStringGenerator.generateTrainString());
            fileWriter.write(System.lineSeparator());
        }
        fileWriter.close();
    }
}
