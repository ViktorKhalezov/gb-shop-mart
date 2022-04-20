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
import ru.gb.gbshopmart.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerIntegTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    void saveProductTest() throws Exception {

        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Product.builder()
                                .title("Computer")
                                .cost(new BigDecimal("1500.0"))
                                .manufactureDate(LocalDate.of(2022, 1, 15))
                                .manufacturer(Manufacturer.builder().name("Intel").build())
                                .categories(Set.of(Category.builder().title("Electronics").build()))
                                .build())))
                .andExpect(status().isCreated());


        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Product.builder()
                                .title("Phone")
                                .cost(new BigDecimal("500.0"))
                                .manufactureDate(LocalDate.of(2021, 12, 30))
                                .manufacturer(Manufacturer.builder().name("Apple").build())
                                .categories(Set.of(Category.builder().title("Devices").build()))
                                .build())))
                .andExpect(status().isCreated());

    }

    @Test
    @Order(2)
    public void findAllTest() throws Exception {

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("Computer"))
                .andExpect(jsonPath("$.[0].cost").value("1500.0"))
                .andExpect(jsonPath("$.[0].manufactureDate").value("15.01.2022"))
                .andExpect(jsonPath("$.[0].manufacturer").value("Intel"))
                .andExpect(jsonPath("$.[0].categories.[0].title").value("Electronics"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].title").value("Phone"))
                .andExpect(jsonPath("$.[1].cost").value("500.0"))
                .andExpect(jsonPath("$.[1].manufactureDate").value("30.12.2021"))
                .andExpect(jsonPath("$.[1].manufacturer").value("Apple"))
                .andExpect(jsonPath("$.[1].categories.[0].title").value("Devices"));
    }

    @Test
    @Order(2)
    public void findByIdTest() throws Exception {

        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Computer"))
                .andExpect(jsonPath("$.cost").value("1500.0"))
                .andExpect(jsonPath("$.manufactureDate").value("15.01.2022"))
                .andExpect(jsonPath("$.manufacturer").value("Intel"))
                .andExpect(jsonPath("$.categories.[0].title").value("Electronics"));
    }


    @Test
    @Order(3)
    public void saveExistingProductTest() throws Exception {

        mockMvc.perform(put("/api/v1/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Product.builder()
                        .title("Laptop")
                        .build())))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Laptop"))
                .andExpect(jsonPath("$.cost").value("1500.0"))
                .andExpect(jsonPath("$.manufactureDate").value("15.01.2022"))
                .andExpect(jsonPath("$.manufacturer").value("Intel"))
                .andExpect(jsonPath("$.categories.[0].title").value("Electronics"));

    }


    @Test
    @Order(4)
    public void deleteByIdTest() throws Exception {

        mockMvc.perform(delete("/api/v1/product/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/product/2"))
                .andExpect(status().isNotFound());

    }

}
