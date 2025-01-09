package airlines.dto.mapper;

import airlines.dto.EmployeeDTO;
import airlines.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEmployeeMapper {
    EmployeeDTO toDTO(Employee employee);
    Employee toEntity(EmployeeDTO employeeDTO);
}
