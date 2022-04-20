package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.gbshopmart.dao.ProductDao;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Mapper
public interface CategoryMapper {

    @Mapping(source = "categoryId", target = "id")
    Category toCategory(CategoryDto categoryDto);

    @Mapping(source = "id", target = "categoryId")
    CategoryDto toCategoryDto(Category category);


    @Mapping(source = "products", target = "products")
    default Set<Product> productDtoSetToProductSet(Set<ProductDto> products, @Context ProductDao productDao) {
        if(products == null) {
            return null;
        }
        Set<Product> productSet = new HashSet<>();
        for(ProductDto productDto : products) {
            Product product = productDao.findById(productDto.getId()).orElseThrow(NoSuchElementException::new);
            productSet.add(product);
        }
        return productSet;
    }

    @Mapping(source = "products", target = "products")
    default Set<ProductDto> productSetToProductDtoSet(Set<Product> products) {
        if(products == null) {
            return null;
        }
        return products.stream()
                .map(product -> ProductDto.builder()
                        .id(product.getId())
                        .title(product.getTitle())
                        .cost(product.getCost())
                        .manufactureDate(product.getManufactureDate())
                        .manufacturer(product.getManufacturer())
                        .products(product.getProducts())
                        .status(product.getStatus())
                        .build())
                .collect(Collectors.toSet());
    }

}
