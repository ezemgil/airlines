package airlines.application.controllers;

import airlines.application.ResponseHandler;
import airlines.dto.AirportDTO;
import airlines.services.interfaces.IAirportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAirportById(
            @Valid @PathVariable Integer id
    ) {
        String message = "Successfully retrieved airport with id " + id;
        AirportDTO airport = airportService.findById(id);
        return ResponseHandler.generateResponse(message, HttpStatus.OK, airport);
    }

    @PostMapping
    public ResponseEntity<Object> saveAirport(
            @Valid @RequestBody AirportDTO airportDTO
    ) {
        String message = "Successfully saved airport";
        AirportDTO airport = airportService.save(airportDTO);
        return ResponseHandler.generateResponse(message, HttpStatus.CREATED, airport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAirport(
            @Valid @PathVariable Integer id,
            @Valid @RequestBody AirportDTO airportDTO
    ) {
        String message = "Successfully updated airport with id " + id;
        AirportDTO airport = airportService.update(id, airportDTO);
        return ResponseHandler.generateResponse(message, HttpStatus.OK, airport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAirport(
            @Valid @PathVariable Integer id
    ) {
        String message = "Successfully deleted airport with id " + id;
        airportService.delete(id);
        return ResponseHandler.generateResponse(message, HttpStatus.OK, null);
    }

}
