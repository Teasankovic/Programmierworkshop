package day3;

import java.util.Scanner;

public class GradeCalculator
{public static void main(String[] args) {


String numberString = "1, 2, 3, 4, 5, 6";
int[] grades = {1, 2, 3, 4, 5, 6};


int sum = 0;
        for (int i = 0; i < grades.length; i++) {
sum += grades[i];
        }


float avg = (float) sum / grades.length;

// 4. Berechnung des Höchstwerts (max)
int max = grades[0];
        for (int n : grades) {
        if (n > max) {
max = n;
            }
                    }
                    System.out.println("Grades: " + numberString);
        System.out.println("Sum is: " + sum);
        System.out.println("Average Grade: " + avg);
        System.out.println("Highest: " + max);
    }
            }