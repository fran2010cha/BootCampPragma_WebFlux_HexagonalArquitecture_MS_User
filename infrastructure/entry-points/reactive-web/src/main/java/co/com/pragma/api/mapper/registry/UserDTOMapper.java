package co.com.pragma.api.mapper.registry;


import co.com.pragma.api.dto.registry.RegistryUserDTO;
import co.com.pragma.api.dto.registry.UserRequestDTO;
import co.com.pragma.api.dto.registry.UserResponseDTO;
import co.com.pragma.model.rol.Rol;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigInteger;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

    User mapToEntity(RegistryUserDTO userDTO);

    RegistryUserDTO mapToDTO(User user);

    UserRequestDTO mapToRequestDTO(User user);

    @Mapping(target = "rol", source = "rolId")
    User mapToEntity(UserRequestDTO userRequestDTO);

    default Rol toRol(Long rolId) {
        if (rolId == null) {
            return null;
        }
        return Rol.builder()
                .uniqueId(BigInteger.valueOf(rolId))
                .build();
    }

    UserResponseDTO mapToResponseDTO(User user);

    User mapToEntity(UserResponseDTO userResponseDTO);
}
