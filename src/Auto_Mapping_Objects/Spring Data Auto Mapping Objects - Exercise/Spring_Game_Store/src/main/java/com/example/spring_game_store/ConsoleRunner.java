package com.example.spring_game_store;

import com.example.spring_game_store.domain.services.ExecutorService.ExecutorService;
import com.example.spring_game_store.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@AllArgsConstructor
@Component
public class ConsoleRunner implements CommandLineRunner {

    private ExecutorService executorService;
    private Scanner scanner;

    @Override
    public void run(String... args) {
        String input = scanner.nextLine();

        try{
            while (!input.equals("Close")){
                System.out.println(this.executorService.execute(input));
                input = scanner.nextLine();
            }
        } catch (AccountNotLoggedInException | AnotherAccountInUseException | NoSuchAccountException | UserAlreadyExistsException | WrongCredentialsException |
                 GameAlreadyExistsException | InsufficientPermissionException | InvalidGameException | GameNotExistsException | NoSuchOperationExists e) {
            System.out.println(e.getMessage());
        }
    }
}
