package airlines.services.implementation;

import airlines.dto.ManufacturerDTO;
import airlines.dto.mapper.IManufacturerMapper;
import airlines.exceptions.notfounds.ManufacturerNotFoundException;
import airlines.repository.IManufacturerRepository;
import airlines.services.interfaces.IManufacturerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManufacturerServiceImpl implements IManufacturerService {

    IManufacturerRepository manufacturerRepository;
    IManufacturerMapper manufacturerMapper;

    @Override
    public ManufacturerDTO findById(Integer id) {
        return manufacturerMapper.toDTO(manufacturerRepository.findById(id).orElseThrow(
                () -> new ManufacturerNotFoundException("Manufacturer with id " + id + " not found")
        ));
    }

}
