package ru.gb.gbapi.category.dto;

import lombok.*;
import ru.gb.gbapi.product.dto.ProductDto;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String title;

    private Set<ProductDto> products;

}
