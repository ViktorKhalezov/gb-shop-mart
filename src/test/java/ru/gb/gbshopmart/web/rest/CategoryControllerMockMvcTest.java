package ru.gb.gbshopmart.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.service.CategoryService;
import ru.gb.gbshopmart.service.ManufacturerService;
import ru.gb.gbshopmart.web.dto.CategoryDto;
import ru.gb.gbshopmart.web.dto.ManufacturerDto;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CategoryController.class)
public class CategoryControllerMockMvcTest {


    @MockBean
    CategoryService categoryService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void findAllTest() throws Exception {
        List<CategoryDto> categories = new ArrayList<>();

        categories.add(CategoryDto.builder().categoryId(1L).title("Food").build());
        categories.add(CategoryDto.builder().categoryId(2L).title("Furniture").build());

        given(categoryService.findAll()).willReturn(categories);

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("Food"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].title").value("Furniture"));
    }


    @Test
    public void findByIdTest() throws Exception {

       given(categoryService.findById(3L)).willReturn(new CategoryDto(3L, "Electronics"));

        mockMvc.perform(get("/api/v1/category/3"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("title")))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.title").value("Electronics"));

    }


    @Test
    public void saveCategoryTest() throws Exception {

        given(categoryService.save(any())).willReturn(new CategoryDto(4L, "Weapon"));

        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Category.builder()
                                .title("Weapon")
                                .build())))
                .andExpect(status().isCreated());
    }

    @Test
    public void saveExistingCategoryTest() throws Exception {

        given(categoryService.findById(1L)).willReturn(new CategoryDto(1L, "Electronics"));

        mockMvc.perform(put("/api/v1/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CategoryDto.builder()
                        .title("Electronics")
                        .build())))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("title")))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Electronics"));
    }


    @Test
    public void deleteByIdTest() throws Exception {

        mockMvc.perform(delete("/api/v1/category/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/category/2"))
                .andExpect(status().isNotFound());

    }


}
