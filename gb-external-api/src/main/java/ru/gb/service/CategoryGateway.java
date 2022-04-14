package ru.gb.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.controller.CategoryApi;
import ru.gb.dto.CategoryDto;

import java.util.List;


@FeignClient(url = "localhost:8080/category", value = "categoryGateway")
public interface CategoryGateway extends CategoryApi {

    @GetMapping(produces = "application/json;charset=UTF-8")
    List<CategoryDto> getCategoryList();

    @GetMapping(value ="/{categoryId}", produces = "application/json;charset=UTF-8")
    ResponseEntity<?> getCategory(@PathVariable("categoryId") Long id);

}
