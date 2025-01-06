package airlines.controllers;

import airlines.application.controllers.AirportController;
import airlines.dto.AirportDTO;
import airlines.exceptions.PageNotFoundException;
import airlines.services.interfaces.IAirportService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AirportControllerTest {

    private static final String GET_AIRPORTS = "getAirports";

    @Mock
    private IAirportService airportService;

    @InjectMocks
    private AirportController airportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class GetAirports {
     @Test @Tag(GET_AIRPORTS)
     void getAirports_ReturnsOKResponse() {
        int page = 0, size = 10;
        String sortBy = "id";
        boolean ascending = true;
        Page<AirportDTO> mockPage = new PageImpl<>(List.of(new AirportDTO()));
        when(airportService.findAll(page, size, sortBy, ascending)).thenReturn(mockPage);
         ResponseEntity<Object> response = airportController.getAirports(page, size, sortBy, ascending);
         assertEquals(200, response.getStatusCode().value());
         assertEquals("Successfully retrieved airports", ((String) ((Map<?, ?>)
                 Objects.requireNonNull(response.getBody())).get("message")));
         verify(airportService, times(1)).findAll(page, size, sortBy, ascending);
     }

        @Test @Tag(GET_AIRPORTS)
        void getAirports_ReturnsEmptyList() {
            int page = 0, size = 10;
            String sortBy = "id";
            boolean ascending = true;
            Page<AirportDTO> mockPage = new PageImpl<>(List.of());
            when(airportService.findAll(page, size, sortBy, ascending)).thenReturn(mockPage);
            ResponseEntity<Object> response = airportController.getAirports(page, size, sortBy, ascending);
            assertEquals(200, response.getStatusCode().value());
            assertEquals("Successfully retrieved airports", ((String) ((Map<?, ?>)
                    Objects.requireNonNull(response.getBody())).get("message")));
            assertEquals(0, ((Page<?>) ((Map<?, ?>) response.getBody()).get("data")).getContent().size());
            verify(airportService, times(1)).findAll(page, size, sortBy, ascending);
        }

        @Test @Tag(GET_AIRPORTS)
        void getAirports_InvalidPage_ThrowsValidationException() {
            int page = -1, size = 10;
            String sortBy = "id";
            boolean ascending = true;
            doThrow(new ConstraintViolationException("getAirports.page: must be greater than or equal to 0", null))
                    .when(airportService).findAll(page, size, sortBy, ascending);
            ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
                    airportController.getAirports(page, size, sortBy, ascending));
            assertEquals("getAirports.page: must be greater than or equal to 0", exception.getMessage());
        }

        @Test @Tag(GET_AIRPORTS)
        void getAirports_PageNotFound_ThrowsException() {
            int page = 1000, size = 10;
            String sortBy = "id";
            boolean ascending = true;
            when(airportService.findAll(page, size, sortBy, ascending))
                    .thenThrow(new PageNotFoundException("No airports found on the requested page"));
            assertThrows(PageNotFoundException.class, () ->
                    airportController.getAirports(page, size, sortBy, ascending)
            );
        }

        @Test @Tag(GET_AIRPORTS)
        void getAirports_UnexpectedError_ThrowsException() {
            int page = 0, size = 10;
            String sortBy = "id";
            boolean ascending = true;
            when(airportService.findAll(page, size, sortBy, ascending))
                    .thenThrow(new RuntimeException("Unexpected error"));
            Exception exception = assertThrows(RuntimeException.class, () ->
                    airportController.getAirports(page, size, sortBy, ascending));
            assertEquals("Unexpected error", exception.getMessage());
        }
    }
}
