package br.com.jonatas.apilogin.util;

import br.com.jonatas.apilogin.model.User;
import br.com.jonatas.apilogin.record.UserDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TokenUtil {
    private static final String ISSUER = "jonatas";
    private static final String SECRET = "jonatas_secret";

    public static String encoder(User user) {
        String token = JWT.create()
                .withSubject(user.getEmail())
                .withIssuer(ISSUER)
                .withClaim("id", user.getId())
                .withExpiresAt(LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-03:00")))
                .sign(Algorithm.HMAC256(SECRET));

        return token;
    }

    public static String decoder(UserDto user) {
        return null;
    }
}
