package com.example.json_exercise;

import com.example.json_exercise.domain.services.CategoryService.CategoryService;
import com.example.json_exercise.domain.services.ProductService.ProductService;
import com.example.json_exercise.domain.services.SeedService.SeedService;
import com.example.json_exercise.domain.services.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;

    private final CategoryService categoryService;

    @Autowired
    public ConsoleRunner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //this.seedService.seedAll(); //works

        //this.productService.findAllByPriceBetweenAndBuyerIsNullOrderByPrice( //works QUE1 - Products in Range
        //BigDecimal.valueOf(500),
        //BigDecimal.valueOf(1000));


        //this.userService
        //.findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName(); //works QUE02 - Successfully Sold Products


        //this.categoryService.getCategoriesSummary(); //works QUE03 - Categories by Products Count


        //this.userService.usersAndProducts(); // works QUE04 - Users and Products
    }
}
