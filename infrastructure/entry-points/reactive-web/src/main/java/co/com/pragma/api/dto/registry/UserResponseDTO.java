package co.com.pragma.api.dto.registry;

import java.math.BigDecimal;

public record UserResponseDTO(
        String idUsuario,
        String nombre,
        String apellido,
        String email,
        String documentoIdentidad,
        String telefono,
        BigDecimal salarioBase,
        Long idRol
) {
}
