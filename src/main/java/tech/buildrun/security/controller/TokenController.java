package tech.buildrun.security.controller;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.buildrun.security.controller.dto.Login;
import tech.buildrun.security.controller.dto.TokenDto;
import tech.buildrun.security.repository.UserRepository;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    private final JwtEncoder encoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenController(JwtEncoder encoder,
                           UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public TokenDto token(@RequestBody Login login) {

        var user = userRepository.findByUsername(login.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(login, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("invalid credentials");
        }

        Instant now = Instant.now();
        long expiry = 300L;

        String scope = user.get().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(login.username())
                .claim("scope", scope)
                .build();

        return new TokenDto(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(), expiry);
    }
}
