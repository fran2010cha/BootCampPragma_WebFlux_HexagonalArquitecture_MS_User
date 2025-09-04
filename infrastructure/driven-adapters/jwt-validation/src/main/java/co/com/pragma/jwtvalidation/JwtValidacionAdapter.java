package co.com.pragma.jwtvalidation;

import co.com.pragma.model.user.gateways.PasswordEncoder;
import co.com.pragma.usecase.login.IJwtTokenProvider;
import co.com.pragma.usecase.validationclient.IValidateJwt;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
@RequiredArgsConstructor
public class JwtValidacionAdapter   implements IValidateJwt ,IJwtTokenProvider, PasswordEncoder {

    private static final String SECRET_KEY = "claveSuperSecretaDeAlMenos32Caracteres!!";
    private static final long EXPIRATION_TIME = 3600_000;

    private static final Logger log = LoggerFactory.getLogger(JwtValidacionAdapter.class);

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    //construimos la clave de firma a partir de la SECRET_KEY
    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Mono<String> createToken(String email) {

        return Mono.fromSupplier(() ->
                Jwts.builder()
                        .setSubject(email)
                        .setIssuer("hu3-service")
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                        .compact()
        );
    }

    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        System.out.println("pass:"+ new BCryptPasswordEncoder().encode("str12345"));
        System.out.println("passss:"+ new BCryptPasswordEncoder().encode(rawPassword));
        System.out.println("passssssss:"+ new BCryptPasswordEncoder().encode(encodedPassword));
        return encoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public Mono<Boolean> validate(String jwt) {
        return Mono.justOrEmpty(jwt)
                .filter(token -> token.startsWith("Bearer "))
                .map(token -> token.substring(7))
                .doOnNext(t -> log.debug("Token extraído: {}", t))
                .map(JwtTokenProvider::validateTokenReactive)
                .doOnNext(valid -> {
                    log.error("JWT inválido para el token extraído");
                })
                .defaultIfEmpty(Mono.just(false))
                .doOnError(e -> log.error("Error al validar el JWT: {}", e.getMessage()))
                .flatMap(mono -> mono);
    }

}
