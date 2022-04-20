package ru.gb.gbshopmart.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Manufacturer;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManufacturerControllerIntegTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @Order(1)
    void testSaveManufacturerTest() throws Exception {

        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(Manufacturer.builder()
                                        .name("Tesla")
                                        .build())))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/manufacturer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Manufacturer.builder()
                                .name("Apple")
                                .build())))
                .andExpect(status().isCreated());

    }

    @Test
    @Order(2)
    public void findAllTest() throws Exception {

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value("Tesla"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].title").value("Apple"));
    }

    @Test
    @Order(2)
    public void findByIdTest() throws Exception {

        mockMvc.perform(get("/api/v1/manufacturer/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("title")))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Tesla"));

    }

    @Test
    @Order(3)
    public void saveExistingManufacturerTest() throws Exception {

        mockMvc.perform(put("/api/v1/manufacturer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Manufacturer.builder()
                        .name("Intel")
                        .build())))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/manufacturer/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("title")))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Intel"));
    }

    @Test
    @Order(4)
    public void deleteByIdTest() throws Exception {

        mockMvc.perform(delete("/api/v1/manufacturer/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/manufacturer/2"))
                .andExpect(status().isNotFound());

    }


}
