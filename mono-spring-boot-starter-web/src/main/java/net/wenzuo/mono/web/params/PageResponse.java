package net.wenzuo.mono.web.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Catch
 * @since 2022-09-08
 */
@Schema(description = "分页响应")
@Data
public class PageResponse<T> {

    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    private int pageNumber = 1;
    /**
     * 每页结果数
     */
    @Schema(description = "每页结果数", example = "20")
    private int pageSize = 20;
    /**
     * 总页数
     */
    @Schema(description = "总页数", example = "0")
    private int totalPage = 0;
    /**
     * 总结果数
     */
    @Schema(description = "总结果数", example = "0")
    private int totalRows = 0;
    /**
     * 数据
     */
    @Schema(description = "数据", example = "[]")
    private List<T> items = new ArrayList<>();

}
