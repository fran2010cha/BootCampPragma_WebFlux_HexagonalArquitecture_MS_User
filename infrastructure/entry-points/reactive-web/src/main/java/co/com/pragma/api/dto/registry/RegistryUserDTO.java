package co.com.pragma.api.dto.registry;

import java.math.BigInteger;
import java.time.LocalDate;

public record RegistryUserDTO(BigInteger id, String nombre, String apellido, String email, LocalDate fechaNacimiento, String telefono, String documentoIdentidad, Long rolId,
                              String direccion, Long salarioBase) {
}
