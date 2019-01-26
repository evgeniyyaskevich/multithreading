package by.epam.javaweb.evgeniyyaskevich.multithreading.exception;


public class IncorrectInputFileException extends Exception {
    public IncorrectInputFileException() {
        super();
    }

    public IncorrectInputFileException(String message) {
        super(message);
    }

    public IncorrectInputFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputFileException(Throwable cause) {
        super(cause);
    }
}
