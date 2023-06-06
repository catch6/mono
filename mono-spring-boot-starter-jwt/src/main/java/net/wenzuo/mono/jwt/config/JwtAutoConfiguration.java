package net.wenzuo.mono.jwt.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.RequiredArgsConstructor;
import net.wenzuo.mono.jwt.properties.JwtProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Base64;

/**
 * @author Catch
 * @since 2023-06-06
 */
@RequiredArgsConstructor
@ComponentScan("net.wenzuo.mono.jwt")
@ConfigurationPropertiesScan("net.wenzuo.mono.jwt.properties")
@ConditionalOnProperty(value = "mono.jwt.enabled", matchIfMissing = true)
public class JwtAutoConfiguration {

    private final JwtProperties jwtProperties;

    @ConditionalOnMissingBean
    @Bean
    public JWSSigner jwsSigner() {
        String secret = jwtProperties.getSecret();
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("jwt secret can not be empty");
        }
        byte[] secretKey = Base64.getDecoder().decode(secret);
        try {
            return new MACSigner(secretKey);
        } catch (KeyLengthException e) {
            throw new RuntimeException(e);
        }
    }

    @ConditionalOnMissingBean
    @Bean
    public JWSVerifier jwsVerifier() {
        String secret = jwtProperties.getSecret();
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("jwt secret can not be empty");
        }
        byte[] secretKey = Base64.getDecoder().decode(secret);
        try {
            return new MACVerifier(secretKey);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

}
