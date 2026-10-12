package day5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




public class MyGame {
    static Scanner scanner = new Scanner(System.in);
    static List<String> inventar = new ArrayList<>();
    private static int checkpoint = 1;

    static void main(String[] args) {
        inventar.add("Spraypaint" + " 🫟");
        inventar.add("gloves" + " 🧤");
        inventar.add("lockpicks" + " 🔑");
        inventar.add("screwdriver" + " 🪛");
        inventar.add("glass cutter" + " 🔪");

        System.out.println("\u001B[1mVIKI's Million Dollar Heist\u001B[0m");

        while (true) {
            switch (checkpoint) {
                case 1:
                    checkpoint1Museum();
                    break;
                case 2:
                    checkpoint2Gallery();
                    break;
                case 3:
                    checkpoint3Security();
                    break;
                case 4:
                    checkpoint4Treasure();
                    break;
                default:
                    System.out.println("Kritischer Fehler: Ungültiger Checkpoint!");
                    scanner.close();
                    System.exit(1);

            }
        }
    }
    private static void checkpoint1Museum() {
        System.out.println("""
                Your name is Viki and you have become the most wanted robber in the 21st Century...
                With your 22 years of age you are wanted in 14 Countries so far but you dont want to stop.
                You do this all for fun.
                Your Trademark is a red Smiley that you leave behind
                That is why your next Million Dollar Heist is planned
                The Louvre and it's Diamonds.... You want them to be yours.
                You already stole $161.777.222 in valuable goods.
                AND YOU DONT WANT TO STOP THE FUN!!!
                """);

        System.out.println("With your Duffelbag and your tools you make your way to the Museum");
        System.out.println("--- 🏁 CHECKPOINT 1: INFRONT OF THE MUSEUM ---\n" +
                "You stand in the shadows of the Louvre.\n" +
                "How do you break in?");
        System.out.println("[1] Use the basement window on the Backside of the Westwing");
        System.out.println("[2] The Roof Hatch directly over the Sculpture Gallery");

        String wahl = holeEingabe();
        if (wahl.equals("1")) {
            System.out.println("\nViki sneeks to the basement window. It's made out of massive steel witih a thick Lock.\n" +
                    "❌ System-Meldung: You don't have a crowbar! Thats the wrong way.");
        } else if (wahl.equals("2")) {
            System.out.println("\nYou skillfully climb the scaffolding on the façade up to the glass roof hatch.\n" +

                    "How do you open it?");
            System.out.println("[1] Smash in the glass with your fist, making a loud noise\"");
            System.out.println("[2] Open the duffel bag and use a tool\"");

            String wahlHatch = holeEingabe(); {

                if (wahlHatch.equals("1")) {
                    System.out.println("\n🚨 The glass shatters loudly! Sirens wail instantly!");
                    triggerGameOver(1);
                } else if (wahlHatch.equals("2")) {
                    if (nutzeItem("glass cutter")) {
                        System.out.println("\nPerfect! You cut a circle into the glass and rappel down.\n");
                        checkpoint = 2; // Aufstieg in den nächsten Abschnitt
                    } else {
                        System.out.println("\nThis item doesn't help here. The lock has no keyhole.\n");
                        // Spieler bleibt bei Checkpoint 1 und wird oben neu gefragt
                    }
                } else {
                    System.out.println("\n❌ Ungültige Eingabe. Bitte wähle 1 oder 2.");
                }
            }

        }
    }
    private static void checkpoint2Gallery() {
        System.out.println("--- 🏁 CHECKPOINT 2: IN THE GALLERY ---\n" +
                "You land in the shadows of the gallery. It is pitch black.\n" +
                "Suddenly, there is a buzzing sound above you—a camera is panning in your direction!");

        System.out.println("[1] Quickly run forward into the next room");
        System.out.println("[2] Immediately throw yourself flat behind the nearest marble statue");

        String wahl = holeEingabe();

        if (wahl.equals("1")) {
            System.out.println("\n🚨 The camera instantly detects your movement. The building is being locked down!");
            triggerGameOver(2);
        } else if (wahl.equals("2")) {
            System.out.println("\nClose call! The camera beam grazes your boot by mere millimeters.");
            checkpoint = 3;
        } else {
            System.out.println("\n❌ Invalid input. Please choose 1, 2, or 3.");
        }
    }

       private static void checkpoint3Security() {
            System.out.println("--- 🏁 CHECKPOINT 3: THE CORRIDOR ---\n" +
                    "You reach the hallway leading to the vault. A night guard is strolling around.\n" +

                    "He is standing right between you and the diamonds. How do you get past him?");

            System.out.println("[1] Camera hacking: Start the robot vacuum at the other end as a distraction");
            System.out.println("[2] Mechanically sabotage the fuse box on the wall");

            String wahl = holeEingabe();

            if (wahl.equals("1")) {
                System.out.println("\n🚨 The distance is too big so the robot vacuum doesn't start\n" +
                        " and the Guard turns around and sees you!");
                        triggerGameOver(3);

            } else if (wahl.equals("2")) {
                System.out.println("\nYou are standing in front of the metal cover of the electrical box.");

                if (nutzeItem("screwdriver")) {
                    System.out.println("\n*Fizz!* A short circuit extinguishes all the lights. The guard curses in the dark.");

                    System.out.println("[1] Use the absolute darkness and walk past quietly");
                    System.out.println("[2] Run loudly while he can't see");

                    String unterWahl = holeEingabe();
                    if (unterWahl.equals("1")) {
                        System.out.println("\nYou slip past him silently, like a ghost.");
                        checkpoint = 4;
                    } else if (unterWahl.equals("2")) {
                        System.out.println("\n🚨 He hears your heavy footsteps and fires blindly in your direction—a hit!");
                        triggerGameOver(3);
                    } else {
                        System.out.println("\n❌ Invalid input. Please choose 1 or 2.");
                    }
                }

            }
        }

    private static void checkpoint4Treasure() {
        System.out.println("--- 🏁 CHECKPOINT 4: THE TREASURY ---\n" +
                "You are standing in the Galerie d'Apollon. Before you, Le Régent and Le Sancy are sparkling!\n" +
                "But red laser beams guard them. How do you proceed?");

        System.out.println("[1] Shatter the display case glass directly using the glass cutter");
        System.out.println("[2] Use the hacking system to disable the lasers for 30 seconds");

        String wahl = holeEingabe();

        if (wahl.equals("1")) {
            System.out.println("\n🚨 The glass has alarm sensors! Iron bars drop down and trap you.");
            triggerGameOver(4);
        } else if (wahl.equals("2")) {
            System.out.println("\nThe lasers go out! A timer ticks away relentlessly: 30... 29... 28...");

            if (nutzeItem("lockpicks")) {
                System.out.println("\n*Click.* The lock pops open! The diamonds land in the duffel bag.\n" +
                        "The alarm sirens start wailing (weight sensor)! You have 5 seconds left.\n" +
                        "Do you leave your signature?");
                System.out.println("[1] No time! Run straight to the escape window");
                System.out.println("[2] Open the duffel bag and use the red spray can");

                String letzteWahl = holeEingabe();
                boolean gueltigeWahl = true;

                if (letzteWahl.equals("2")) {
                    System.out.println("\n*Shhh!* A large, red smiley face adorns the empty display case. Gotta have style!");
                } else if (letzteWahl.equals("1")) {
                    System.out.println("Why did you chicken out now? That was always your Trademark ☹️😤");
                } else {
                    System.out.println("\n❌ Invalid input. Please choose 1 or 2.");
                    gueltigeWahl = false;
                }

                if (gueltigeWahl) {
                    System.out.println("\n🏆 😏😜--- MISSION SUCCESSFUL ---🤑🥴 🏆\n" +
                            "You jump through the window onto the escape roof. Down below, the police search in vain.\n" +
                            "Paris is asleep, but Viki remains the undisputed number one in the world!");
                    scanner.close();
                    System.exit(0);
                }
            } else {
                System.out.println("\n🚨 Wrong tool! Time is running out; the lasers are frying your gear.");
                triggerGameOver(4);
            }
        } else {
            System.out.println("\n❌ Invalid Input. Please choose 1 or 2.");
        }
    }


    private static String holeEingabe() {
        System.out.print("Your Choice: ");
        return scanner.nextLine().trim();
    }

    private static boolean nutzeItem(String korrektesItem) {
        System.out.println("\n🎒 [DEIN DUFFELBAG] Enthält aktuell:");
        for (String item : inventar) {
            System.out.println("   - " + item);
        }
        System.out.print("What Item do you type in? (english Name): ");
        String eingabe = scanner.nextLine().trim().toLowerCase();

        return eingabe.equals(korrektesItem);
    }

    private static void triggerGameOver(int aktuellerCheckpoint) {
        System.out.println("\n🛑 --- GAME OVER --- 🛑\n" +
                "You got caught or made a mistake.");

        boolean entschieden = false;
        while (!entschieden) {
            System.out.println("[1] Continue on (" + aktuellerCheckpoint + ") last Checkpoint");
            System.out.println("[2] End Game");

            String wahl = holeEingabe();

            if (wahl.equals("1")) {
                checkpoint = aktuellerCheckpoint;
                entschieden = true;
            } else if (wahl.equals("2")) {
                System.out.println("Thank you for playing!");
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("❌ Ungültige Eingabe. Bitte wähle 1 oder 2.");
            }

        }
    }
}