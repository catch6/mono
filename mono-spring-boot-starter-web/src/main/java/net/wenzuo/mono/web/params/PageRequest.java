package net.wenzuo.mono.web.params;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * @author Catch
 * @since 2022-09-08
 */
@Schema(description = "分页请求")
@Data
public class PageRequest {

    /**
     * 页码
     */
    @Schema(description = "页码", example = "1", defaultValue = "1")
    @Min(value = 1, message = "页码最小为 1")
    private int pageNumber = 1;
    /**
     * 每页结果数
     */
    @Schema(description = "每页结果数", example = "20", defaultValue = "20")
    @Min(value = 1, message = "每页结果数最小为 1")
    private int pageSize = 20;

}
