package co.com.pragma.api.mapper.registry;


import co.com.pragma.api.dto.registry.RegistryUserDTO;
import co.com.pragma.api.dto.registry.UserRequestDTO;
import co.com.pragma.api.dto.registry.UserResponseDTO;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

    User mapToEntity(RegistryUserDTO userDTO);

    RegistryUserDTO mapToDTO(User user);

    UserRequestDTO mapToRequestDTO(User user);

    User mapToEntity(UserRequestDTO userRequestDTO);

    UserResponseDTO mapToResponseDTO(User user);

    User mapToEntity(UserResponseDTO userResponseDTO);
}
