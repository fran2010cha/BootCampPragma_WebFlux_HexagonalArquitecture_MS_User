package co.com.pragma.api.dto.registry;


import io.swagger.v3.oas.annotations.media.Schema;

public record UserRequestDTO(
        @Schema(
                description = "ID del usuario",
                example = "1",
                minimum = "0"
        )
        String id,

        @Schema(
                description = "Nombre del usuario",
                example = "Francisco",
                minLength = 2,
                maxLength = 50
        )
        String nombre,

        @Schema(
                description = "Apellido del usuario",
                example = "Aristizabal",
                minLength = 2,
                maxLength = 50
        )
        String apellido,

        @Schema(
                description = "Correo electrónico del usuario",
                example = "francisco.aristy@gamil.com",
                format = "email"
        )
        String email,

        @Schema(
                description = "Número de documento de identidad",
                example = "1234567890",
                minLength = 6,
                maxLength = 20
        )
        String documentoIdentidad,

        @Schema(
                description = "Dirreccion residencia",
                example = "cra 25 41 63",
                minLength = 6,
                maxLength = 50
        )
        String direccion,

        @Schema(
                description = "rol Id ",
                example = "1",
                minLength = 6,
                maxLength = 50
        )
        Long rolId,

        @Schema(
                description = "Fecha de nacimiento",
                example = "12-02-1994",
                minLength = 6,
                maxLength = 20
        )
        String fechaNacimiento,

        @Schema(
                description = "Teléfono de contacto del usuario",
                example = "+57 3001234567",
                pattern = "^\\+?[0-9 ]{7,15}$"
        )
        String telefono,

        @Schema(
                description = "Salario base del usuario",
                example = "2500000",
                minimum = "0"
        )
        Long salarioBase,

        @Schema(
                description = "Contraseña en texto plano",
                example = "miPassword123")
        String passwordHash
) {
}
