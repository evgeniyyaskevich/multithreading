package by.epam.javaweb.evgeniyyaskevich.multithreading.filereader;

import by.epam.javaweb.evgeniyyaskevich.multithreading.exception.IncorrectInputFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {

    private static final Logger LOGGER = LogManager.getLogger(FileReader.class);
    private List<String> linesOfFile;

    public FileReader(File file) throws IncorrectInputFileException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file.getPath()))) {
            linesOfFile = bufferedReader.lines().collect(Collectors.toList());
        }
        catch (IOException e) {
            LOGGER.warn("File wasn`t open.");
            throw new IncorrectInputFileException("File can`t be opened.");
        }
    }

    public boolean hasNextLine() {
        return linesOfFile.size() > 0;
    }

    public String readLine() {
        return linesOfFile.remove(0);
    }
}