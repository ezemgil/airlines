package airlines.exceptions.notfounds;

public class DstNotFoundException extends RuntimeException {
    public DstNotFoundException(String message) {
        super(message);
    }
}
