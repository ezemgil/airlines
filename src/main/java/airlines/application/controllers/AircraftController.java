package airlines.application.controllers;

import airlines.application.ResponseHandler;
import airlines.dto.AircraftDTO;
import airlines.services.interfaces.IAircraftService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Min;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/aircraft")
public class AircraftController {
    private final IAircraftService aircraftService;

    @GetMapping
    public ResponseEntity<Object> getAircraft(
            @Valid @RequestParam(defaultValue = "0") @Min(value = 0) int page,
            @Valid @RequestParam(defaultValue = "10") @Min(value = 1) int size,
            @RequestParam(defaultValue = "id")  String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        String message = "Successfully retrieved aircraft";
        Page<AircraftDTO> aircraft = aircraftService.findAll(page, size, sortBy, ascending);
        return ResponseHandler.generateResponse(message, HttpStatus.OK, aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAircraftById(
            @Valid @PathVariable Integer id
    ) {
        String message = "Successfully retrieved aircraft";
        AircraftDTO aircraft = aircraftService.findById(id);
        return ResponseHandler.generateResponse(message, HttpStatus.OK, aircraft);
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody AircraftDTO aircraftDTO) {
        String message = "Aircraft created successfully";
        AircraftDTO aircraft = aircraftService.save(aircraftDTO);
        return ResponseHandler.generateResponse(message, HttpStatus.CREATED, aircraft);
    }
}