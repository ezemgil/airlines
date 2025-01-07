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
    private static final String GET_AIRPORT_BY_ID = "getAirportById";
    private static final String CREATE_AIRPORT = "createAirport";
    private static final String UPDATE_AIRPORT = "updateAirport";
    private static final String DELETE_AIRPORT = "deleteAirport";

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

    @Nested
    class GetAirportById {
        @Test @Tag(GET_AIRPORT_BY_ID)
        void getAirportById_ReturnsOKResponse() {
            Integer id = 1;
            AirportDTO mockAirport = new AirportDTO();
            when(airportService.findById(id)).thenReturn(mockAirport);
            ResponseEntity<Object> response = airportController.getAirportById(id);
            assertEquals(200, response.getStatusCode().value());
            assertEquals("Successfully retrieved airport with id " + id, ((String) ((Map<?, ?>)
                    Objects.requireNonNull(response.getBody())).get("message")));
            verify(airportService, times(1)).findById(id);
        }

        @Test @Tag(GET_AIRPORT_BY_ID)
        void getAirportById_AirportNotFound_ThrowsException() {
            Integer id = 1;
            when(airportService.findById(id)).thenThrow(new RuntimeException("Airport with id " + id + " not found"));
            Exception exception = assertThrows(RuntimeException.class, () ->
                    airportController.getAirportById(id));
            assertEquals("Airport with id 1 not found", exception.getMessage());
        }

        @Test @Tag(GET_AIRPORT_BY_ID)
        void getAirportById_UnexpectedError_ThrowsException() {
            Integer id = 1;
            when(airportService.findById(id)).thenThrow(new RuntimeException("Unexpected error"));
            Exception exception = assertThrows(RuntimeException.class, () ->
                    airportController.getAirportById(id));
            assertEquals("Unexpected error", exception.getMessage());
        }
    }

    @Nested
    class CreateAirport {
        @Test @Tag(CREATE_AIRPORT)
        void createAirport_ReturnsCreatedResponse() {
            AirportDTO mockAirport = new AirportDTO();
            when(airportService.save(mockAirport)).thenReturn(mockAirport);
            ResponseEntity<Object> response = airportController.saveAirport(mockAirport);
            assertEquals(201, response.getStatusCode().value());
            assertEquals("Successfully saved airport", ((String) ((Map<?, ?>)
                    Objects.requireNonNull(response.getBody())).get("message")));
            verify(airportService, times(1)).save(mockAirport);
        }

        @Test @Tag(CREATE_AIRPORT)
        void createAirport_UnexpectedError_ThrowsException() {
            AirportDTO mockAirport = new AirportDTO();
            when(airportService.save(mockAirport)).thenThrow(new RuntimeException("Unexpected error"));
            Exception exception = assertThrows(RuntimeException.class, () ->
                    airportController.saveAirport(mockAirport));
            assertEquals("Unexpected error", exception.getMessage());
        }
    }

    @Nested
    class UpdateAirport {
        @Test @Tag(UPDATE_AIRPORT)
        void updateAirport_ReturnsOKResponse() {
            Integer id = 1;
            AirportDTO mockAirport = new AirportDTO();
            when(airportService.update(id, mockAirport)).thenReturn(mockAirport);
            ResponseEntity<Object> response = airportController.updateAirport(id, mockAirport);
            assertEquals(200, response.getStatusCode().value());
            assertEquals("Successfully updated airport with id 1", ((String) ((Map<?, ?>)
                    Objects.requireNonNull(response.getBody())).get("message")));
            verify(airportService, times(1)).update(id, mockAirport);
        }

        @Test @Tag(UPDATE_AIRPORT)
        void updateAirport_AirportNotFound_ThrowsException() {
            Integer id = 1;
            AirportDTO mockAirport = new AirportDTO();
            when(airportService.update(id, mockAirport)).thenThrow(new RuntimeException("Airport with id 1 not found"));
            Exception exception = assertThrows(RuntimeException.class, () ->
                    airportController.updateAirport(id, mockAirport));
            assertEquals("Airport with id 1 not found", exception.getMessage());
        }

        @Test @Tag(UPDATE_AIRPORT)
        void updateAirport_UnexpectedError_ThrowsException() {
            Integer id = 1;
            AirportDTO mockAirport = new AirportDTO();
            when(airportService.update(id, mockAirport)).thenThrow(new RuntimeException("Unexpected error"));
            Exception exception = assertThrows(RuntimeException.class, () ->
                    airportController.updateAirport(id, mockAirport));
            assertEquals("Unexpected error", exception.getMessage());
        }
    }

    @Nested
    class DeleteAirport {
        @Test @Tag(DELETE_AIRPORT)
        void deleteAirport_ReturnsOKResponse() {
            Integer id = 1;
            ResponseEntity<Object> response = airportController.deleteAirport(id);
            assertEquals(200, response.getStatusCode().value());
            assertEquals("Successfully deleted airport with id 1", ((String) ((Map<?, ?>)
                    Objects.requireNonNull(response.getBody())).get("message")));
            verify(airportService, times(1)).delete(id);
        }

        @Test @Tag(DELETE_AIRPORT)
        void deleteAirport_AirportNotFound_ThrowsException() {
            Integer id = 1;
            doThrow(new RuntimeException("Airport with id 1 not found")).when(airportService).delete(id);
            Exception exception = assertThrows(RuntimeException.class, () ->
                    airportController.deleteAirport(id));
            assertEquals("Airport with id 1 not found", exception.getMessage());
        }

        @Test @Tag(DELETE_AIRPORT)
        void deleteAirport_UnexpectedError_ThrowsException() {
            Integer id = 1;
            doThrow(new RuntimeException("Unexpected error")).when(airportService).delete(id);
            Exception exception = assertThrows(RuntimeException.class, () ->
                    airportController.deleteAirport(id));
            assertEquals("Unexpected error", exception.getMessage());
        }
    }
}
