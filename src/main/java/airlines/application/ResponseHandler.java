package airlines.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object data) {
        Map<String, Object> meta = new HashMap<>();
        meta.put("status", status.value());
        meta.put("timestamp", Timestamp.valueOf(java.time.LocalDateTime.now()));

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("meta", meta);

        if (data != null) {
            String key = status.is2xxSuccessful() ? "data" : "errors";
            response.put(key, data);
        }

        return new ResponseEntity<>(response, status);
    }
}