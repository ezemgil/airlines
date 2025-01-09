package airlines.dto.mapper;

import airlines.dto.PassengerDTO;
import airlines.model.Passenger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPassengerMapper {
    PassengerDTO toDTO(Passenger passenger);
    Passenger toEntity(PassengerDTO passengerDTO);
}
