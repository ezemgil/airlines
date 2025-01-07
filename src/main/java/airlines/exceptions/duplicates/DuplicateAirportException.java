package airlines.exceptions.duplicates;

public class DuplicateAirportException extends RuntimeException {
    public DuplicateAirportException(String message) {
        super(message);
    }
}
