package net.wenzuo.mono.test.core.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.core.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Slf4j
class JsonUtilsTest {

    @Test
    void toStringTest() {
        VO vo = new VO();
        vo.setDateTime(LocalDateTime.now());
        vo.setDate(LocalDate.now());
        vo.setTime(LocalTime.now());
        String result = JsonUtils.toString(vo);
        log.info("result: {}", result);
    }

    @Data
    static class VO {

        private LocalDateTime dateTime;
        private LocalDate date;
        private LocalTime time;

    }

}