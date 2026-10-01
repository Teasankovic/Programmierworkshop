package day5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HEISTGAME {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> inventar = new ArrayList<>();
    private static int checkpoint = 1;
    private static String holeEingabe() {
        System.out.println("Your Choice: ");
        return scanner.nextLine().trim();
    }


    public static void main(String[] args) {
     inventar.add("red spraypaint can" + " 🫟");
     inventar.add("leather gloves" + " 🧤");
     inventar.add("screwdriver" + " 🪛");
     inventar.add("lockpicks" + " 🔑");
     inventar.add("glass cutter" + " 🔪");

        System.out.println("\u001B[1mVIKI's Million Dollar Heist\\u001B[0m\");


     /*   while (true) {
            switch (checkpoint) {
                case 1:
                    checkpoint1Museum();
                    break;
                case 2:
                    checkpoint2Galerie();
                    break;
                case 3:
                    checkpoint3Wache();
                    break;
                case 4:
                    checkpoint4Schatzkammer();
                    break;
                default:
                    System.out.println("Kritischer Fehler: Ungültiger Checkpoint!");
                    scanner.close();
                    System.exit(1)*/
            }
        }
    }


    private static void checkpoint1Museum() {
        System.out.println("Your name is Viki and you have become the most wanted robber in the 21st Century...");
        System.out.println("With your 22 years of age you are wanted in 14 Countries so far but you dont want to stop.");
        System.out.println("You do this all for fun.");
        System.out.println("Your Trademark is a red Smiley that you leave behind");
        System.out.println("That is why your next Million Dollar Heist is planned");
        System.out.println("The Louvre and it's Diamonds.... You want them to be yours.");
        System.out.println("You already stole $161.777.222 in valuable goods.");
        System.out.println("AND YOU DONT WANT TO STOP THE FUN!!!");
        System.out.println("");
        System.out.println("With your Duffelbag and your tools you make your way to the Museum");
        System.out.println("--- 🚩 CHECKPOINT 1: INFRONT OF THE MUSEUM ---\n" +
                "You stand in the shadows of the Louvre.\n" +
                "How do you break in?");
        System.out.println("");

        System.out.println("[1] Use the basement window on the Backside of the Westwing");
        System.out.println("[2] The Roof Hatch directly over the Sculpture Gallery");

         String wahl = "holeEingabe"();


        if(wahl.equals("1")) {
            System.out.println("\nViki sneeks to the basement window. It's made out of massive steel witih a thick Lock.\n" +
                    "SYSTEM MELDUNG: You dont have a crowbar! That's the wrong way. Check your Duffelbag")
        }else if(wahl.equals("2")) {
            System.out.println("\nYou skillfully climb the scaffolding on the façade up to the glass roof hatch.\n" +
                    "How do you open it?");
        }



















































