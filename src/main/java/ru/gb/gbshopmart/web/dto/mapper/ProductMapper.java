package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.dao.ManufacturerDao;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.entity.Product;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Mapper(uses = ManufacturerMapper.class)
public interface ProductMapper {
    Product toProduct(ProductDto productDto, @Context ManufacturerDao manufacturerDao);

    ProductDto toProductDto(Product product);

    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(NoSuchElementException::new);
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }

    @Mapping(source = "categories", target = "categories")
    default Set<Category> categoryDtoSetToCategorySet(Set<CategoryDto> categories, @Context CategoryDao categoryDao) {
        if(categories == null) {
            return null;
        }
        Set<Category> categorySet = new HashSet<>();
        for(CategoryDto categoryDto : categories) {
            Category category = categoryDao.findByTitle(categoryDto.getTitle()).orElseThrow(NoSuchElementException::new);
            categorySet.add(category);
        }
        return categorySet;
    }

    @Mapping(source = "categories", target = "categories")
    default Set<CategoryDto> categorySetToCategoryDtoSet(Set<Category> categories) {
        if(categories == null) {
            return null;
        }
        return categories.stream()
                .map(category -> CategoryDto.builder()
                        .id(category.getId())
                        .title(category.getTitle())
                        .products(category.getProducts())
                        .build())
                .collect(Collectors.toSet());
    }

}
