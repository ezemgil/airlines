package airlines.exceptions.notfounds;

public class DstOffsetNotFoundException extends RuntimeException {
    public DstOffsetNotFoundException(String message) {
        super(message);
    }
}
