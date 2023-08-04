package com.example.socksstore.controller;

import com.example.socksstore.model.SocksEntity;
import com.example.socksstore.repository.SocksRepository;
import com.example.socksstore.service.SocksService;
import com.example.socksstore.utils.SocksMapper;
import com.example.socksstore.utils.Validator;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SocksController.class)
class SocksControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private Validator validator;
    @SpyBean
    private SocksService service;
    @SpyBean
    private SocksMapper mapper;
    @MockBean
    private SocksRepository repository;
    JSONObject dto = new JSONObject();
    JSONObject invalidDto = new JSONObject();
    private final String color = "black";
    private final short cotton = 11;
    private final long result = 55L;
    private final long quantity = 20;

    @BeforeEach
    void setUp() {
        dto.put("color", color);
        dto.put("quantity", quantity);
        dto.put("cottonPart", cotton);
        invalidDto.put("color", color);
        invalidDto.put("quantity", -150);
        dto.put("cotton", cotton);
    }

    @Test
    void registerIncomeSocksTestOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/income")
                        .content(dto.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void registerIncomeSocksTestBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/income")
                        .content(invalidDto.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerOutcomeSocksTestOk() throws Exception {
        SocksEntity entity = new SocksEntity(color, cotton, quantity);
        when(repository.findByColorIgnoreCaseAndCottonPart(color, cotton)).thenReturn(entity);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/outcome")
                        .content(dto.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void registerOutcomeSocksTestBadRequest() throws Exception {
        when(repository.findByColorIgnoreCaseAndCottonPart(color, cotton)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/outcome")
                        .content(dto.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCountSocksInvalidOperationTest() throws Exception {
        String oper = "jhvh";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/socks", color, oper, cotton)
                        .param("color", color)
                        .param("operation", oper)
                        .param("cottonPart", String.valueOf(cotton))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCountSocksEqualOperationTest() throws Exception {
        String oper = "equal";
        when(repository.getEqual(color, cotton)).thenReturn(result);
        validOperationsTestTemplate(color, oper, cotton);
    }

    @Test
    void getCountSocksMorelOperationTest() throws Exception {
        String oper = "moreThan";
        when(repository.getMoreThan(color, cotton)).thenReturn(result);
        validOperationsTestTemplate(color, oper, cotton);
    }

    @Test
    void getCountSocksLessOperationTest() throws Exception {
        String oper = "lessThan";
        when(repository.getLessThan(color, cotton)).thenReturn(result);
        validOperationsTestTemplate(color, oper, cotton);
    }

    private void validOperationsTestTemplate(String color, String oper, short cotton) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/socks", color, oper, cotton)
                        .param("color", color)
                        .param("operation", oper)
                        .param("cottonPart", String.valueOf(cotton))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk()).andExpect(content().string(String.valueOf(result)));
    }
}