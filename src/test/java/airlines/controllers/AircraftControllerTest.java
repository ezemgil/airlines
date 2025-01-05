package airlines.controllers;

import airlines.application.controllers.AircraftController;
import airlines.dto.AircraftDTO;
import airlines.exceptions.PageNotFoundException;
import airlines.services.interfaces.IAircraftService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AircraftControllerTest {

    @Mock
    private IAircraftService aircraftService;

    @InjectMocks
    private AircraftController aircraftController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAircraft_ReturnsOkResponse() {
        // Arrange: Mock a valid response from the service
        int page = 0, size = 10;
        String sortBy = "id";
        boolean ascending = true;
        Page<AircraftDTO> mockPage = new PageImpl<>(List.of(new AircraftDTO()));
        when(aircraftService.findAll(page, size, sortBy, ascending)).thenReturn(mockPage);

        // Act: Call the controller method
        ResponseEntity<Object> response = aircraftController.getAircraft(page, size, sortBy, ascending);

        // Assert: Verify the response status and message
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Successfully retrieved aircraft", ((String) ((Map<?, ?>) response.getBody()).get("message")));
        verify(aircraftService, times(1)).findAll(page, size, sortBy, ascending);
    }

    @Test
    void getAircraft_ReturnsEmptyList() {
        // Arrange: Mock an empty page
        int page = 0, size = 10;
        String sortBy = "id";
        boolean ascending = true;
        Page<AircraftDTO> mockPage = new PageImpl<>(List.of());
        when(aircraftService.findAll(page, size, sortBy, ascending)).thenReturn(mockPage);

        // Act: Call the controller method
        ResponseEntity<Object> response = aircraftController.getAircraft(page, size, sortBy, ascending);

        // Assert: Verify the empty list in the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Successfully retrieved aircraft", ((String) ((Map<?, ?>) response.getBody()).get("message")));
        assertEquals(0, ((Page<?>) ((Map<?, ?>) response.getBody()).get("data")).getContent().size());
        verify(aircraftService, times(1)).findAll(page, size, sortBy, ascending);
    }

    @Test
    void getAircraft_InvalidPage_ThrowsValidationException() {
        // Arrange: Set an invalid page number
        int page = -1, size = 10;
        String sortBy = "id";
        boolean ascending = true;

        // Mock the service to throw ConstraintViolationException
        doThrow(new ConstraintViolationException("getAircraft.page: must be greater than or equal to 0", null))
                .when(aircraftService).findAll(page, size, sortBy, ascending);

        // Act & Assert: Expect a ConstraintViolationException
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
                aircraftController.getAircraft(page, size, sortBy, ascending)
        );
        assertEquals("getAircraft.page: must be greater than or equal to 0", exception.getMessage());
    }


    @Test
    void getAircraft_PageNotFound_ThrowsException() {
        // Arrange: Mock a PageNotFoundException
        int page = 1000, size = 10;
        String sortBy = "id";
        boolean ascending = true;
        when(aircraftService.findAll(page, size, sortBy, ascending))
                .thenThrow(new PageNotFoundException("No aircraft found on the requested page"));

        // Act & Assert: Expect a PageNotFoundException
        assertThrows(PageNotFoundException.class, () ->
                aircraftController.getAircraft(page, size, sortBy, ascending)
        );
    }

    @Test
    void getAircraft_UnexpectedError_ThrowsException() {
        // Arrange: Mock a RuntimeException
        int page = 0, size = 10;
        String sortBy = "id";
        boolean ascending = true;
        when(aircraftService.findAll(page, size, sortBy, ascending))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert: Expect a RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () ->
                aircraftController.getAircraft(page, size, sortBy, ascending)
        );
        assertEquals("Unexpected error", exception.getMessage());
    }

    @Test
    void getAircraftById_ReturnsOkResponse() {
        // Arrange: Mock a valid response from the service
        int id = 1;
        AircraftDTO mockAircraft = new AircraftDTO();
        when(aircraftService.findById(id)).thenReturn(mockAircraft);

        // Act: Call the controller method
        ResponseEntity<Object> response = aircraftController.getAircraftById(id);

        // Assert: Verify the response status and message
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Successfully retrieved aircraft", ((String) ((Map<?, ?>) response.getBody()).get("message")));
        verify(aircraftService, times(1)).findById(id);
    }

    @Test
    void getAircraftById_AircraftNotFound_ThrowsException() {
        // Arrange: Mock an AircraftNotFoundException
        int id = 1;
        when(aircraftService.findById(id))
                .thenThrow(new PageNotFoundException("Aircraft with id " + id + " not found"));

        // Act & Assert: Expect a PageNotFoundException
        assertThrows(PageNotFoundException.class, () ->
                aircraftController.getAircraftById(id));
    }

    @Test
    void getAircraftById_UnexpectedError_ThrowsException() {
        // Arrange: Mock a RuntimeException
        int id = 1;
        when(aircraftService.findById(id))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert: Expect a RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () ->
                aircraftController.getAircraftById(id));
        assertEquals("Unexpected error", exception.getMessage());
    }

    @Test
    void getAircraftById_InvalidId_ThrowsValidationException() {
        // Arrange: Set an invalid id
        int id = -1;

        // Mock the service to throw ConstraintViolationException
        doThrow(new ConstraintViolationException("getAircraftById.id: must be greater than or equal to 0", null))
                .when(aircraftService).findById(id);

        // Act & Assert: Expect
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
                aircraftController.getAircraftById(id));
        assertEquals("getAircraftById.id: must be greater than or equal to 0", exception.getMessage());
    }
}
