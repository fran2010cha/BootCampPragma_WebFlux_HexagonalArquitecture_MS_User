package co.com.pragma.model.user;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void crearUsuarioConAllArgsConstructor() {
        BigInteger id = BigInteger.ONE;
        String nombre = "Carlos";
        String apellido = "Pérez";
        String fechaNacimiento = "12-02-1994";
        String direccion = "cra 25 41 40";
        String telefono = "3001234567";
        String email = "carlos@example.com";
        BigDecimal salarioBase = new BigDecimal(14000000);
        String documentoIdentidad = "4452466";
        Long rolId = 1L;


        User usuario = new User(
                id,
                nombre,
                apellido,
                fechaNacimiento,
                direccion,
                telefono,
                email,
                salarioBase,
                documentoIdentidad,
                rolId
        );

        assertNotNull(usuario);
        assertEquals(id, usuario.getId());
        assertEquals(nombre, usuario.getNombre());
        assertEquals(apellido, usuario.getApellido());
        assertEquals(fechaNacimiento, usuario.getFechaNacimiento());
        assertEquals(direccion, usuario.getDireccion());
        assertEquals(telefono, usuario.getTelefono());
        assertEquals(email, usuario.getEmail());
        assertEquals(salarioBase, usuario.getSalarioBase());
        assertEquals(documentoIdentidad, usuario.getDocumentoIdentidad());
        assertEquals(rolId, usuario.getRolId());

    }

}
