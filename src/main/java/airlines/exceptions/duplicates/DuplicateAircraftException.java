package airlines.exceptions.duplicates;

public class DuplicateAircraftException extends RuntimeException {
    public DuplicateAircraftException(String message) {
        super(message);
    }
}
