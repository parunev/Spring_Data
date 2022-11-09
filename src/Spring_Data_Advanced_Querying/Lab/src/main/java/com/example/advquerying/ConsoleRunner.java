package com.example.advquerying;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public ConsoleRunner(ShampooService shampooService, IngredientService ingredientService){
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //String name = scanner.nextLine();

       /* List<String> ingredients = new ArrayList<>();

        while (!name.isBlank()) {
            ingredients.add(name);

            name = scanner.nextLine();
        }


        */
        //for (Shampoo shampoo: this.shampooService.findWithIngredientCountLessThan(count)) {
        //    System.out.println(shampoo);
        //}

       // for (Ingredient ingredient:this.ingredientService.selectByNames(ingredients)) {
       //     System.out.println(ingredient);
       // }

       // System.out.println(this.shampooService.countWithPriceLowerThan(price));

       // this.ingredientService.deleteByName(name);
        this.ingredientService.updateAllPrice();
    }
}
