package net.wenzuo.mono.test.core.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.core.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Test
    void toObject() {
        List<VO> list = new ArrayList<>();
        VO vo1 = new VO();
        vo1.setDateTime(LocalDateTime.now());
        vo1.setDate(LocalDate.now());
        vo1.setTime(LocalTime.now());
        list.add(vo1);
        VO vo2 = new VO();
        vo2.setDateTime(LocalDateTime.now());
        vo2.setDate(LocalDate.now());
        vo2.setTime(LocalTime.now());
        list.add(vo2);
        String jsonString = JsonUtils.toString(list);
        List<VO> vos = JsonUtils.toObject(jsonString, List.class, VO.class);
        Set<VO> voSet = JsonUtils.toObject(jsonString, Set.class, VO.class);
        log.info("vos: {}", vos);
        log.info("voSet: {}", voSet);
    }

    @Test
    void toPrettyString() {
        VO vo = new VO();
        vo.setDateTime(LocalDateTime.now());
        vo.setDate(LocalDate.now());
        vo.setTime(LocalTime.now());
        String result = JsonUtils.toPrettyString(vo);
        log.info("result: {}", result);
    }

    @Data
    static class VO {

        private LocalDateTime dateTime;
        private LocalDate date;
        private LocalTime time;

    }

}