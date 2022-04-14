package ru.gb.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.controller.ProductApi;
import ru.gb.dto.ProductDto;

import java.util.List;

@FeignClient(url = "localhost:8080/product", value = "productGateway")
public interface ProductGateway extends ProductApi {

    @GetMapping(produces = "application/json;charset=UTF-8")
    List<ProductDto> getProductList();

    @GetMapping(value = "/{productId}", produces = "application/json;charset=UTF-8")
    ResponseEntity<?> getProduct(@PathVariable("productId") Long id);

}
