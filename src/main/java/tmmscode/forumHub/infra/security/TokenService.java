package tmmscode.forumHub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import tmmscode.forumHub.domain.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${forumhub.security.token.secret}")
    private String secret;

    @Value("${forumhub.security.token.issuer}")
    private String jwtIssuer;

    @Value("${forumhub.security.token.expiration-hours}")
    private Long tokenExpirationHours;

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(jwtIssuer)
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(expireDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Failed to generate JWT: ", exception);
        }
    }

    private Instant expireDate(){
        return LocalDateTime.now().plusHours(tokenExpirationHours).toInstant(ZoneOffset.of("-03:00"));
    }


    public String getJwtTokenFromRequest(HttpServletRequest request){
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    public DecodedJWT getDecodedJWT(String token){
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(jwtIssuer)
                    .build();

            return decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception){
            throw new RuntimeException("JWT Token is invalid or expired!");
        }
    }

    public String getSubjectFromToken(String jwtToken) {
        return getDecodedJWT(jwtToken).getSubject();
    }

        // TROCAR PARA REQUEST HEADER
    public Long getUserIDfromRequest(HttpServletRequest request) {
        var jwtToken = getJwtTokenFromRequest(request);
        var decodedJWT = getDecodedJWT(jwtToken);

        return decodedJWT.getClaim("id").asLong();
    }

}
