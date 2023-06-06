package net.wenzuo.mono.test.jwt.service;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.jwt.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Slf4j
@RequiredArgsConstructor
@SpringBootTest
class JwtServiceTest {

    @Resource
    private JwtService jwtService;

    @Test
    void sign() {
        String signed = jwtService.sign("test");
        log.info("signed: {}", signed);
    }

    @Test
    void parse() {
        String signed = jwtService.sign("test");
        log.info("signed: {}", signed);
        String parsed = jwtService.parse(signed, String.class);
        log.info("parsed: {}", parsed);
    }

}