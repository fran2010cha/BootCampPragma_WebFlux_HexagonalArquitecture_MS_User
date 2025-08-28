package co.com.pragma.r2dbc.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.BigInteger;

@Table("users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
    @Id
    @Column("user_id")
    private BigInteger idUsuario;
    private String nombre;
    private String apellido;
    @Column("fecha_nacimiento")
    private String fechaNacimiento;
    private String direccion;
    private String telefono;
    private String email;
    @Column("salario_base")
    private BigDecimal salarioBase;
    @Column("documento_identidad")
    private String documentoIdentidad;
    @Column("rol_id")
    private Long rolId;
}
