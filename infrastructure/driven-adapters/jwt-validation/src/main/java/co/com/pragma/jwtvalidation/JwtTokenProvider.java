package co.com.pragma.jwtvalidation;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtTokenProvider {


    private static final String SECRET_KEY = "claveSuperSecretaDeAlMenos32Caracteres!!";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    public static Mono<String> validateTokenReactive(String token) {
        return Mono.fromCallable(() -> {
            try {
                Claims claims = Jwts.parser() // ✅ en 0.13.0
                        .setSigningKey(KEY)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                return   claims.get("ROL", Integer.class); // 👈 recuperas el rol
               //  true;
            } catch (JwtException | IllegalArgumentException e) {
                log.error("❌ Token inválido: {}", e.getMessage());
                //return false;
                return null;
            }
        }).flatMap(rol -> rol != null ? Mono.just(rol.toString()) : Mono.empty());
    }

}
