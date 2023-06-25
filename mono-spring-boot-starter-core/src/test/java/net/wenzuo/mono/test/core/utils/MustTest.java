package net.wenzuo.mono.test.core.utils;

import net.wenzuo.mono.core.exception.ServerException;
import net.wenzuo.mono.core.utils.Must;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Catch
 * @since 2023-06-19
 */
class MustTest {

    @Test
    void isTrue() {
        assertThrows(ServerException.class, () -> Must.isTrue(false, "false message"));
    }

}