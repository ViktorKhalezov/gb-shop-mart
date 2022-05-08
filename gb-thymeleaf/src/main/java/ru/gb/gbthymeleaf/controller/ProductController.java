package ru.gb.gbthymeleaf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbthymeleafwinter.entity.Product;
import ru.gb.gbthymeleafwinter.service.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    private final CartService cartService;


    @GetMapping("/all")
    public String getProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product-list";
    }


    @GetMapping("/{productId}")
    public String info(Model model, @PathVariable(name = "productId") Long id) {
        ProductDto productDto;
        if (id != null) {
            productDto = productService.findById(id);
        } else {
            return "redirect:/product/all";
        }
        model.addAttribute("product", productDto);
        return "product-info";
    }


    @GetMapping
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        ProductDto productDto;

        if (id != null) {
            productDto = productService.findById(id);
        } else {
            productDto = new ProductDto();
        }
        model.addAttribute("product", productDto);
        return "product-form";
    }


    @PostMapping
    public String saveProduct(ProductDto productDto) {
        productService.save(productDto);
        return "redirect:/product/all";
    }


    @GetMapping("/delete")
    public String deleteById(@RequestParam(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }


    @GetMapping("/cart")
    public String showCart(Model model) {
        model.addAttribute("products", cartService.getProducts());
        return "cart";
    }

    @GetMapping("/addToCart")
    public String addProductToCart(@RequestParam(name = "id") Long id){
        cartService.addProduct(productService.findById(id));
        return "redirect:/product/all";
    }

    @GetMapping("/deleteFromCart")
    public String deleteProductFromCart(@RequestParam(name = "id") Long id) {
        cartService.deleteProduct(productService.findById(id));
        return "redirect:/product/cart";
    }

}
