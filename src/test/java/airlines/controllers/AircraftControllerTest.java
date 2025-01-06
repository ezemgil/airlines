package airlines.controllers;

import airlines.application.controllers.AircraftController;
import airlines.dto.AircraftDTO;
import airlines.exceptions.PageNotFoundException;
import airlines.services.interfaces.IAircraftService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AircraftControllerTest {

    private static final String GET_AIRCRAFT = "getAircraft";
    private static final String GET_AIRCRAFT_BY_ID = "getAircraftById";
    private static final String CREATE_AIRCRAFT = "createAircraft";
    private static final String UPDATE_AIRCRAFT = "updateAircraft";

    @Mock
    private IAircraftService aircraftService;

    @InjectMocks
    private AircraftController aircraftController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class GetAircraftTests {
        @Test @Tag(GET_AIRCRAFT)
        void getAircraft_ReturnsOkResponse() {
            int page = 0, size = 10;
            String sortBy = "id";
            boolean ascending = true;
            Page<AircraftDTO> mockPage = new PageImpl<>(List.of(new AircraftDTO()));
            when(aircraftService.findAll(page, size, sortBy, ascending)).thenReturn(mockPage);
            ResponseEntity<Object> response = aircraftController.getAircraft(page, size, sortBy, ascending);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("Successfully retrieved aircraft", ((String) ((Map<?, ?>) response.getBody()).get("message")));
            verify(aircraftService, times(1)).findAll(page, size, sortBy, ascending);
        }

        @Test @Tag(GET_AIRCRAFT)
        void getAircraft_ReturnsEmptyList() {
            int page = 0, size = 10;
            String sortBy = "id";
            boolean ascending = true;
            Page<AircraftDTO> mockPage = new PageImpl<>(List.of());
            when(aircraftService.findAll(page, size, sortBy, ascending)).thenReturn(mockPage);
            ResponseEntity<Object> response = aircraftController.getAircraft(page, size, sortBy, ascending);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("Successfully retrieved aircraft", ((String) ((Map<?, ?>) response.getBody()).get("message")));
            assertEquals(0, ((Page<?>) ((Map<?, ?>) response.getBody()).get("data")).getContent().size());
            verify(aircraftService, times(1)).findAll(page, size, sortBy, ascending);
        }

        @Test @Tag(GET_AIRCRAFT)
        void getAircraft_InvalidPage_ThrowsValidationException() {
            int page = -1, size = 10;
            String sortBy = "id";
            boolean ascending = true;
            doThrow(new ConstraintViolationException("getAircraft.page: must be greater than or equal to 0", null))
                    .when(aircraftService).findAll(page, size, sortBy, ascending);
            ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
                    aircraftController.getAircraft(page, size, sortBy, ascending));
            assertEquals("getAircraft.page: must be greater than or equal to 0", exception.getMessage());
        }

        @Test @Tag(GET_AIRCRAFT)
        void getAircraft_PageNotFound_ThrowsException() {
            int page = 1000, size = 10;
            String sortBy = "id";
            boolean ascending = true;
            when(aircraftService.findAll(page, size, sortBy, ascending))
                    .thenThrow(new PageNotFoundException("No aircraft found on the requested page"));
            assertThrows(PageNotFoundException.class, () ->
                    aircraftController.getAircraft(page, size, sortBy, ascending)
            );
        }

        @Test @Tag(GET_AIRCRAFT)
        void getAircraft_UnexpectedError_ThrowsException() {
            int page = 0, size = 10;
            String sortBy = "id";
            boolean ascending = true;
            when(aircraftService.findAll(page, size, sortBy, ascending))
                    .thenThrow(new RuntimeException("Unexpected error"));
            Exception exception = assertThrows(RuntimeException.class, () ->
                    aircraftController.getAircraft(page, size, sortBy, ascending));
            assertEquals("Unexpected error", exception.getMessage());
        }
    }

    @Nested
    class GetAircraftByIdTests {
        @Test @Tag(GET_AIRCRAFT_BY_ID)
        void getAircraftById_ReturnsOkResponse() {
            int id = 1;
            AircraftDTO mockAircraft = new AircraftDTO();
            when(aircraftService.findById(id)).thenReturn(mockAircraft);
            ResponseEntity<Object> response = aircraftController.getAircraftById(id);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("Successfully retrieved aircraft", ((String) ((Map<?, ?>) response.getBody()).get("message")));
            verify(aircraftService, times(1)).findById(id);
        }

        @Test @Tag(GET_AIRCRAFT_BY_ID)
        void getAircraftById_AircraftNotFound_ThrowsException() {
            int id = 1;
            when(aircraftService.findById(id))
                    .thenThrow(new PageNotFoundException("Aircraft with id " + id + " not found"));
            assertThrows(PageNotFoundException.class, () ->
                    aircraftController.getAircraftById(id));
        }

        @Test @Tag(GET_AIRCRAFT_BY_ID)
        void getAircraftById_UnexpectedError_ThrowsException() {
            int id = 1;
            when(aircraftService.findById(id))
                    .thenThrow(new RuntimeException("Unexpected error"));
            Exception exception = assertThrows(RuntimeException.class, () ->
                    aircraftController.getAircraftById(id));
            assertEquals("Unexpected error", exception.getMessage());
        }

        @Test @Tag(GET_AIRCRAFT_BY_ID)
        void getAircraftById_InvalidId_ThrowsValidationException() {
            int id = -1;
            doThrow(new ConstraintViolationException("getAircraftById.id: must be greater than or equal to 0", null))
                    .when(aircraftService).findById(id);
            ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
                    aircraftController.getAircraftById(id));
            assertEquals("getAircraftById.id: must be greater than or equal to 0", exception.getMessage());
        }
    }

    @Nested
    class CreateAircraftTests {
        @Test @Tag(CREATE_AIRCRAFT)
        void createAircraft_ReturnsOkResponse() {
            AircraftDTO mockAircraft = new AircraftDTO();
            when(aircraftService.save(mockAircraft)).thenReturn(mockAircraft);
            ResponseEntity<Object> response = aircraftController.create(mockAircraft);
            assertEquals(201, response.getStatusCodeValue());
            assertEquals("Aircraft created successfully", ((String) ((Map<?, ?>) response.getBody()).get("message")));
            verify(aircraftService, times(1)).save(mockAircraft);
        }

        @Test @Tag(CREATE_AIRCRAFT)
        void createAircraft_InvalidAircraft_ThrowsValidationException() {
            AircraftDTO mockAircraft = new AircraftDTO();
            doThrow(new ConstraintViolationException("createAircraft: invalid aircraft", null))
                    .when(aircraftService).save(mockAircraft);
            ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
                    aircraftController.create(mockAircraft));
            assertEquals("createAircraft: invalid aircraft", exception.getMessage());
        }

        @Test @Tag(CREATE_AIRCRAFT)
        void createAircraft_UnexpectedError_ThrowsException() {
            AircraftDTO mockAircraft = new AircraftDTO();
            when(aircraftService.save(mockAircraft))
                    .thenThrow(new RuntimeException("Unexpected error"));
            Exception exception = assertThrows(RuntimeException.class, () ->
                    aircraftController.create(mockAircraft));
            assertEquals("Unexpected error", exception.getMessage());
        }

        @Test @Tag(CREATE_AIRCRAFT)
        void createAircraft_NullAircraft_ThrowsException() {
            when(aircraftService.save(null))
                    .thenThrow(new IllegalArgumentException("Aircraft cannot be null"));
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    aircraftController.create(null));
            assertEquals("Aircraft cannot be null", exception.getMessage());
        }

        @Test @Tag(CREATE_AIRCRAFT)
        void createAircraft_EmptyAircraft_ThrowsException() {
            when(aircraftService.save(new AircraftDTO()))
                    .thenThrow(new IllegalArgumentException("Aircraft cannot be empty"));
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    aircraftController.create(new AircraftDTO()));
            assertEquals("Aircraft cannot be empty", exception.getMessage());
        }
    }

    @Nested
    class UpdateAircraftTests{
        @Test @Tag(UPDATE_AIRCRAFT)
        void updateAircraft_ReturnsOkResponse() {
            int id = 1;
            AircraftDTO mockAircraft = new AircraftDTO();
            when(aircraftService.update(id, mockAircraft)).thenReturn(mockAircraft);
            ResponseEntity<Object> response = aircraftController.update(id, mockAircraft);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("Aircraft updated successfully", ((String) ((Map<?, ?>) response.getBody()).get("message")));
            verify(aircraftService, times(1)).update(id, mockAircraft);
        }

        @Test @Tag(UPDATE_AIRCRAFT)
        void updateAircraft_InvalidAircraft_ThrowsValidationException() {
            int id = 1;
            AircraftDTO mockAircraft = new AircraftDTO();
            doThrow(new ConstraintViolationException("updateAircraft: invalid aircraft", null))
                    .when(aircraftService).update(id, mockAircraft);
            ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
                    aircraftController.update(id, mockAircraft));
            assertEquals("updateAircraft: invalid aircraft", exception.getMessage());
        }

        @Test @Tag(UPDATE_AIRCRAFT)
        void updateAircraft_UnexpectedError_ThrowsException() {
            int id = 1;
            AircraftDTO mockAircraft = new AircraftDTO();
            when(aircraftService.update(id, mockAircraft))
                    .thenThrow(new RuntimeException("Unexpected error"));
            Exception exception = assertThrows(RuntimeException.class, () ->
                    aircraftController.update(id, mockAircraft));
            assertEquals("Unexpected error", exception.getMessage());
        }

        @Test @Tag(UPDATE_AIRCRAFT)
        void updateAircraft_NullAircraft_ThrowsException() {
            int id = 1;
            when(aircraftService.update(id, null))
                    .thenThrow(new IllegalArgumentException("Aircraft cannot be null"));
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    aircraftController.update(id, null));
            assertEquals("Aircraft cannot be null", exception.getMessage());
        }

        @Test @Tag(UPDATE_AIRCRAFT)
        void updateAircraft_EmptyAircraft_ThrowsException() {
            int id = 1;
            when(aircraftService.update(id, new AircraftDTO()))
                    .thenThrow(new IllegalArgumentException("Aircraft cannot be empty"));
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    aircraftController.update(id, new AircraftDTO()));
            assertEquals("Aircraft cannot be empty", exception.getMessage());
        }
    }
}
