package airlines.exceptions.notfounds;

public class ContinentNotFoundException extends RuntimeException {
    public ContinentNotFoundException(String message) {
        super(message);
    }
}
