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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}