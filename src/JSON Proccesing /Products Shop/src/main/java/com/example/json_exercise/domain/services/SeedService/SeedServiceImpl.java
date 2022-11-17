package com.example.json_exercise.domain.services.SeedService;

import com.example.json_exercise.domain.dtos.categoryDTOs.CategoryImportDTO;
import com.example.json_exercise.domain.dtos.productDTOs.ProductImportDTO;
import com.example.json_exercise.domain.dtos.userDTOs.UserImportDTO;
import com.example.json_exercise.domain.entities.Category;
import com.example.json_exercise.domain.entities.Product;
import com.example.json_exercise.domain.entities.User;
import com.example.json_exercise.domain.repositories.CategoryRepository;
import com.example.json_exercise.domain.repositories.ProductRepository;
import com.example.json_exercise.domain.repositories.UserRepository;
import com.example.json_exercise.domain.services.SeedService.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

import static com.example.json_exercise.constants.Paths.*;
import static com.example.json_exercise.constants.Utils.GSON;
import static com.example.json_exercise.constants.Utils.MODEL_MAPPER;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsers() throws IOException {
        if (this.userRepository.count() == 0){
            final FileReader fileReader = new FileReader(USER_JSON_PATH.toFile());

            final List<User> users = Arrays.stream(GSON.fromJson(fileReader, UserImportDTO[].class))
                    .map(userImportDTO -> MODEL_MAPPER.map(userImportDTO, User.class)).toList();

            this.userRepository.saveAll(users);
            fileReader.close();
        }
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0){
            final FileReader fileReader = new FileReader(CATEGORY_JSON_PATH.toFile());

            final List<Category> categories = Arrays.stream(GSON.fromJson(fileReader, CategoryImportDTO[].class))
                    .map(categoryImportDTO -> MODEL_MAPPER.map(categoryImportDTO, Category.class)).toList();

            this.categoryRepository.saveAll(categories);
            fileReader.close();
        }

    }

    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count() == 0) {
            final FileReader fileReader = new FileReader(PRODUCT_JSON_PATH.toFile());

            final List<Product> productStream = Arrays.stream(GSON.fromJson(fileReader, ProductImportDTO[].class))
                    .map(productImportDTO -> MODEL_MAPPER.map(productImportDTO, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategories)
                    .toList();

            this.productRepository.saveAll(productStream);

            fileReader.close();
        }
    }

    private Product  setRandomCategories(Product product) {
        final Random random = new Random();
        final int numberOfCategories = random.nextInt((int) this.categoryRepository.count());

        Set<Category> categories = new HashSet<>();
        IntStream.range(0, numberOfCategories)
                .forEach(number ->{
                    Category category =
                            this.categoryRepository
                                    .getRandomEntity()
                                    .orElseThrow(NoSuchElementException::new);
                    categories.add(category);
                });

        product.setCategories(categories);

        return product;
    } //hashCode

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0 ){
            User buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);

            while (buyer.equals(product.getSeller())){
                buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
            }

            product.setBuyer(buyer);
        } //hashCode

        return product;
    }

    private Product setRandomSeller(Product product) {
        final User seller = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);

        product.setSeller(seller);

        return product;
    } //hashCode
}
