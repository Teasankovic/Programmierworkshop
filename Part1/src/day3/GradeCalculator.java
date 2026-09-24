package day3;

import java.util.Scanner;

public class GradeCalculator
{public static void main(String[] args) {
    /*Your Mission:
    Store a set of grades in an array or a List
- Write a method that calculates the sum of all grades
- Write a method that calculates the average
- Write a method that finds the highest grade
- Use a loop (your choice; for, while or do-while) inside each method
- Print a clear summary to the console*/


            String number = "1, 2, 3, 4, 5, 6";
            int[] grades = {1, 2, 3, 4, 5,6};
            int sum = 0;

            System.out.println("Grades: " + number);

            for (int i = 1; i <= 6; i++) {
                sum= sum + i;
            }
            System.out.println("Sum is " + sum);


            System.out.prinln("Avarage Grade" avarage);

            for (int i)
            System.out.println("Highest" + highestNum);
        }
    }
