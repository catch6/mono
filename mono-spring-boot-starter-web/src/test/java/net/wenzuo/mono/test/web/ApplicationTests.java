package net.wenzuo.mono.test.web;

import jakarta.annotation.Resource;
import net.wenzuo.mono.core.utils.JsonUtils;
import net.wenzuo.mono.test.web.params.TestReq;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;

/**
 * @author Catch
 * @since 2023-06-06
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Resource
    private MockMvc mockMvc;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/get"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getQuery() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/get/query")
                                              .queryParam("id", "1")
                                              .queryParam("name", "你好test"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getFile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/get/file"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void postFormUrlencoded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/post/x-www-form-urlencoded")
                                              .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                              .param("id", "1")
                                              .param("name", "你好test"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void postJson() throws Exception {
        TestReq req = new TestReq();
        req.setId(1L);
        req.setName("你好test");
        mockMvc.perform(MockMvcRequestBuilders.post("/test/post/json")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(JsonUtils.toString(req)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void postFormData() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "测试图片.png", MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(ResourceUtils.getFile("classpath:测试图片.png")));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/test/post/form-data")
                                              .file(file)
                                              .param("name", "你好test"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

}
