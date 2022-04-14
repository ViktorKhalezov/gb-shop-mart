package ru.gb.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.dto.ProductDto;
import ru.gb.service.ProductGateway;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop")
public class ShopController {

    private final ProductGateway productGateway;

    @GetMapping
    public List<ProductDto> getProducts(){
        return productGateway.getProductList();
    }

}
