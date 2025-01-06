package airlines.application.controllers;

import airlines.application.ResponseHandler;
import airlines.dto.AirportDTO;
import airlines.services.interfaces.IAirportService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {
    private final IAirportService airportService;

    @GetMapping
    public ResponseEntity<Object> getAirports(
            @RequestParam(defaultValue = "0") @Min(value = 0) int page,
            @RequestParam(defaultValue = "10") @Min(value = 1) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        String message = "Successfully retrieved airports";
        Page<AirportDTO> airports = airportService.findAll(page, size, sortBy, ascending);
        return ResponseHandler.generateResponse(message, HttpStatus.OK, airports);
    }

}
