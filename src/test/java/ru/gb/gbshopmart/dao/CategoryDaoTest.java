package ru.gb.gbshopmart.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.gb.gbshopmart.config.ShopConfig;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.entity.Product;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(ShopConfig.class)
public class CategoryDaoTest {

    private static String CATEGORY_NAME = "Food";

    @Autowired
    CategoryDao categoryDao;

    @Test
    public void saveTest() {
        Category category = Category.builder().title(CATEGORY_NAME).build();

        Category savedCategory = categoryDao.save(category);

        assertAll(
                () -> assertEquals(1L, savedCategory.getId()),
                () -> assertEquals(CATEGORY_NAME, savedCategory.getTitle()),
                () -> assertEquals(0, savedCategory.getVersion()),
                () -> assertEquals("User", savedCategory.getCreatedBy()),
                () -> assertEquals("User", savedCategory.getLastModifiedBy()),
                () -> assertNotNull(savedCategory.getCreatedDate()),
                () -> assertNotNull(savedCategory.getLastModifiedDate())
        );
    }

    @Test
    public void findByIdTest() {
        Category category = Category.builder().title(CATEGORY_NAME).build();

        categoryDao.save(category);

        Optional<Category> categoryFromDbOptional = categoryDao.findById(category.getId());

        Category categoryFromDb = categoryFromDbOptional.get();

        assertAll(
                () -> assertEquals(2L, categoryFromDb.getId()),
                () -> assertEquals(CATEGORY_NAME, categoryFromDb.getTitle()),
                () -> assertEquals(0, categoryFromDb.getVersion()),
                () -> assertEquals("User", categoryFromDb.getCreatedBy()),
                () -> assertEquals("User", categoryFromDb.getLastModifiedBy()),
                () -> assertNotNull(categoryFromDb.getCreatedDate()),
                () -> assertNotNull(categoryFromDb.getLastModifiedDate())
        );
    }


}
