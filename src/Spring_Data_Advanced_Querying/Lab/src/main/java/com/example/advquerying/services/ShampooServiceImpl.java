package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class ShampooServiceImpl implements ShampooService {

    private ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findByBrand(String brand) {
        return this.shampooRepository.findByBrand(brand);
    }

    @Override
    public List<Shampoo> findByBrandAndSize(String brand, String size) {
        Size parsed = Size.valueOf(size.toUpperCase(Locale.ROOT));

        return this.shampooRepository.findByBrandAndSize(brand, parsed);
    }

    @Override
    public List<Shampoo> findBySize(String size) {
        Size parsed = Size.valueOf(size.toUpperCase(Locale.ROOT));

        return this.shampooRepository.findBySizeOrderByIdDesc(parsed);
    }

    @Override
    public List<Shampoo> findByIngredient(String ingredient) {

        return this.shampooRepository.findByIngredient(ingredient);
    }

    @Override
    public List<Shampoo> findByIngredients(List<String> ingredients) {
        return this.shampooRepository.findByIngredients(ingredients);
    }

    @Override
    public List<Shampoo> findBySizeOrLabelId(String size, long labelId) {
        Size parsed = Size.valueOf(size.toUpperCase(Locale.ROOT));

        return this.shampooRepository.findBySizeOrLabelId(parsed, labelId);
    }

    @Override
    public List<Shampoo> findWithPriceGreaterThan(String price){
        BigDecimal parsed = new BigDecimal(price);

        return this.shampooRepository.findByPriceGreaterThanOrderByPriceDesc(parsed);
    }

    @Override
    public long countWithPriceLowerThan(String price) {
        BigDecimal parsed = new BigDecimal(price);
        return this.shampooRepository.countByPriceLessThan(parsed);
    }

    @Override
    public List<Shampoo> findWithIngredientCountLessThan(int count) {
        return this.shampooRepository.findByIngredientCountLessThan(count);
    }
}