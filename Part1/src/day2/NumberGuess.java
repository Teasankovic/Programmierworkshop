package day2;

import java.util.Scanner;
import java.util.Random;

public class NumberGuess {
    public static void main(String[] args) {
        //Scanner initialisieren
        Scanner scanner = new Scanner(System.in);

      //Dieses random benutzen für die zahlen
        Random random = new Random();
        int secretNumber = random.nextInt(100) + 1;

        int tries = 0;
        boolean hasGuessedCorrectly = false;

        while (!hasGuessedCorrectly) {
            System.out.print("Guess the number (1-100): ");


            int guess = scanner.nextInt();
            tries++;


            if (guess > secretNumber) {
                System.out.println("Too high!");
            } else if (guess < secretNumber) {
                System.out.println("Too low!");
            } else {
                System.out.println("Correct! You guessed it in " + tries + " tries.");
                hasGuessedCorrectly = true;
            }
        }


        scanner.close();
    }
}
