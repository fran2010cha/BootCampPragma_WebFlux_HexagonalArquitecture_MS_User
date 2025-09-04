package co.com.pragma.model.user;
import co.com.pragma.model.rol.Rol;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private BigInteger id;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String direccion;
    private String telefono;
    private String email;
    private BigDecimal salarioBase;
    private String documentoIdentidad;
    private Rol rol;
    private String passwordHash;
    private int intentosFallidos;
    private LocalDateTime bloqueadoHasta;
}
