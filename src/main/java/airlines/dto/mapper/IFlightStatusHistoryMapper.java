package airlines.dto.mapper;

import airlines.dto.FlightStatusHistoryDTO;
import airlines.model.FlightStatusHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFlightStatusHistoryMapper {
    FlightStatusHistoryDTO toDTO(FlightStatusHistory flightStatusHistory);
    FlightStatusHistory toEntity(FlightStatusHistoryDTO flightStatusHistoryDTO);
}
