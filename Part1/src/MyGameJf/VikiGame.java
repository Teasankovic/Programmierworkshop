package MyGameJf;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import day5.Inventory;


public class VikiGame {

    private JFrame window;
    private JLabel imageLabel;
    private JTextArea storyTextArea;
    private JPanel choicePanel;
    private JTextArea inventoryArea;

    // Nutzt exakt deine bestehende Inventar-Klasse!
    private static Inventory inventar = new Inventory();
    private int checkpoint = 1;

    // Variablen für die Text-Einfließ-Animation
    private Timer typewriterTimer;
    private String fullText = "";
    private int textIndex = 0;

    public static void main(String[] args) {
        // Swing-Anwendungen sollten auf dem Event Dispatch Thread gestartet werden
        SwingUtilities.invokeLater(() -> new VikiGame().createGameScreen());
    }

    public void createGameScreen() {
        // Window erstellen (JFrame)
        window = new JFrame("VIKI's Million Dollar Heist");
        window.setSize(900, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(new BorderLayout(10, 10));

        // 1. FIRST-PERSON BILD (Oben)
        imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(900, 350));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        imageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        imageLabel.setForeground(Color.GRAY);
        window.add(imageLabel, BorderLayout.NORTH);

        // Zentrales Panel für Text und Interaktionen im unteren Bereich
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBackground(Color.BLACK);

        // 2. TEXTBEREICH FÜR DIE STORY (Mitte)
        storyTextArea = new JTextArea();
        storyTextArea.setBackground(Color.BLACK);
        storyTextArea.setForeground(new Color(223, 115, 255)); // Neon-Grün / Matrix-Look
        storyTextArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        storyTextArea.setLineWrap(true);
        storyTextArea.setWrapStyleWord(true);
        storyTextArea.setEditable(false);
        storyTextArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(storyTextArea);
        scrollPane.setPreferredSize(new Dimension(900, 160));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        // 3. INTERAKTIONS-BEREICH (Ganz unten: Buttons links, Inventar rechts)
        JPanel interactionPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        interactionPanel.setBackground(Color.BLACK);

        // Panel für die dynamischen Entscheidungs-Buttons
        choicePanel = new JPanel(new GridLayout(2, 2, 5, 5));
        choicePanel.setBackground(Color.BLACK);
        interactionPanel.add(choicePanel);

        // Inventar-Anzeige (Rechts unten)
        inventoryArea = new JTextArea();
        inventoryArea.setBackground(new Color(20, 20, 20));
        inventoryArea.setForeground(Color.MAGENTA);
        inventoryArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inventoryArea.setEditable(false);
        inventoryArea.setMargin(new Insets(5, 5, 5, 5));
        inventoryArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "🎒 DUFFELBAG (INVENTAR)", 0, 0, null, Color.MAGENTA));

        interactionPanel.add(inventoryArea);
        bottomPanel.add(interactionPanel, BorderLayout.SOUTH);
        window.add(bottomPanel, BorderLayout.CENTER);

        window.setLocationRelativeTo(null); // Bildschirm zentrieren
        window.setVisible(true);

        // Aktualisiere die Inventar-Anzeige das erste Mal
        updateInventoryUI();

        // Starte das Spiel mit dem Intro
        starteIntro();
    }

    // Lässt den Text Buchstabe für Buchstabe einfließen
    public void animateText(String text) {
        fullText = text;
        textIndex = 0;
        storyTextArea.setText("");

        if (typewriterTimer != null && typewriterTimer.isRunning()) {
            typewriterTimer.stop();
        }

        // Alle 25ms wird ein neuer Buchstabe angehängt
        typewriterTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textIndex < fullText.length()) {
                    storyTextArea.append(String.valueOf(fullText.charAt(textIndex)));
                    textIndex++;
                } else {
                    typewriterTimer.stop();
                }
            }
        });
        typewriterTimer.start();
    }

    // Aktualisiert die Anzeige des Inventars basierend auf deiner Klasse
    public void updateInventoryUI() {
        StringBuilder sb = new StringBuilder();
        java.util.List<String> items = inventar.getItems();
        for (int i = 0; i < items.size(); i++) {
            sb.append("  [").append(i + 1).append("] ").append(items.get(i)).append("\n");
        }
        inventoryArea.setText(sb.toString());
    }

    // Setzt das First-Person-Bild oder zeigt einen Platzhalter an
    private void ladeBild(String dateiname) {
        String pfad = "images/" + dateiname;
        File file = new File(pfad);
        if (file.exists()) {
            imageLabel.setText("");

            // Lädt das originale Bild
            ImageIcon originalIcon = new ImageIcon(pfad);
            Image originalImage = originalIcon.getImage();

            // Berechnet die Skalierung, ohne das Pixel-Art zu verwaschen
            // (Image.SCALE_DEFAULT behält bei hochskaliertem Pixelart oft die scharfen Kanten)
            Image scaledImage = originalImage.getScaledInstance(900, 350, Image.SCALE_DEFAULT);

            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            imageLabel.setIcon(null);
            imageLabel.setText("[ Bild-Platzhalter: " + dateiname + " nicht gefunden ]");
        }
    }


    // Entfernt alle alten Buttons, um Platz für neue Entscheidungen zu machen
    private void resetButtons() {
        choicePanel.removeAll();
        choicePanel.revalidate();
        choicePanel.repaint();
    }

    // Erstellt einen standardisierten Button für die Story
    private JButton erstelleButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.addActionListener(action);
        return btn;
    }

    // --- AB HIER FOLGT DEINE SPIELLOGIK, UMGEWANDELT IN DIE GUI ---

    private void starteIntro() {
        ladeBild("intro.jpg");
        animateText("VIKI's Million Dollar Heist\n\n" +
                "Your name is Viki and you have become the most wanted robber in the 21st Century...\n" +
                "With your 22 years of age you are wanted in 14 Countries so far but you dont want to stop.\n" +
                "You do this all for fun. Your Trademark is a red Smiley that you leave behind.\n" +
                "That is why your next Million Dollar Heist is planned: The Louvre and it's Diamonds...\n\n" +
                "Are you ready? We have to hide our fingerprints...");

        resetButtons();
        JButton btnGloves = erstelleButton("Put on Gloves 🧤", e -> {
            // Nutzt deine Logik: Überprüfung, ob Gloves existieren
            if (inventar.containsItem("gloves")) {
                checkpoint1Museum();
            } else {
                animateText("Critical Error: You don't have gloves in your bag!");
            }
        });
        choicePanel.add(btnGloves);
    }

    private void checkpoint1Museum() {
        checkpoint = 1;
        ladeBild("louvre_outside.jpg");
        animateText("With your Duffelbag and your tools you make your way to the Museum.\n\n" +
                "--- 🏁 CHECKPOINT 1: INFRONT OF THE MUSEUM ---\n" +
                "You stand in the shadows of the Louvre. How do you break in?");

        resetButtons();
        JButton btn1 = erstelleButton("Use basement window (Westwing)", e -> {
            animateText("❌ System-Message: You don't have a crowbar! Thats kinda the wrong way.");
        });
        JButton btn2 = erstelleButton("Climb to the Roof Hatch", e -> checkpoint1Hatch());

        choicePanel.add(btn1);
        choicePanel.add(btn2);
    }

    private void checkpoint1Hatch() {
        ladeBild("roof_hatch.jpg");
        animateText("You skillfully climb the scaffolding on the façade up to the glass roof hatch.\n" +
                "How do you open it?");

        resetButtons();
        JButton btn1 = erstelleButton("Smash glass with fist 👊", e -> {
            animateText("🚨 The glass shatters loudly! Sirens wail instantly!");
            triggerGameOver();
        });
        JButton btn2 = erstelleButton("Use Glass Cutter 🔪", e -> {
            if (inventar.containsItem("glass cutter")) {
                checkpoint2Gallery();
            } else {
                animateText("❌ You look in your bag, but you don't find a glass cutter!");
            }
        });

        choicePanel.add(btn1);
        choicePanel.add(btn2);
    }

    private void checkpoint2Gallery() {
        checkpoint = 2;
        ladeBild("gallery.jpg");
        animateText("Perfect! You cut a circle into the glass and rappel down.\n\n" +
                "--- 🏁 CHECKPOINT 2: IN THE GALLERY ---\n" +
                "You land in the shadows of the gallery. It is pitch black.\n" +
                "Suddenly, there is a buzzing sound above you—a camera is panning in your direction!");

        resetButtons();
        JButton btn1 = erstelleButton("Run forward into next room", e -> {
            animateText("🚨 The camera instantly detects your movement. The building is being locked down!");
            triggerGameOver();
        });
        JButton btn2 = erstelleButton("Hide behind marble statue 🗿", e -> checkpoint3Security());

        choicePanel.add(btn1);
        choicePanel.add(btn2);
    }

    private void checkpoint3Security() {
        checkpoint = 3;
        ladeBild("corridor.jpg");
        animateText("Close call! The camera beam grazes your boot by mere millimeters.\n\n" +
                "--- 🏁 CHECKPOINT 3: THE CORRIDOR ---\n" +
                "You reach the hallway leading to the vault. A night guard is strolling around.\n" +
                "He is standing right between you and the diamonds. How do you get past him?");
        resetButtons();
        JButton btn1 = erstelleButton("Hack robot vacuum (Distraction)", e -> {
            animateText("🚨 The distance is too big so the robot vacuum doesn't start and the Guard turns around and sees you!");
            triggerGameOver();
        });
        JButton btn2 = erstelleButton("Sabotage the fuse box ⚡", e -> checkpoint3FuseBox());
        choicePanel.add(btn1);
        choicePanel.add(btn2);
    }
    private void checkpoint3FuseBox() {
        ladeBild("fusebox.jpg");
        animateText("You are standing in front of the metal cover of the electrical box.");
        resetButtons();
        JButton btnScrewdriver = erstelleButton("Use Screwdriver 🪛", e -> {
            if (inventar.containsItem("screwdriver")) {
                checkpoint3Darkness();
            } else {
                animateText("❌ You need a tool to open this box.");
            }
        });
        choicePanel.add(btnScrewdriver);
    }
    private void checkpoint3Darkness() {
        ladeBild("darkness.jpg");
        animateText("Fizz! A short circuit extinguishes all the lights. The guard curses in the dark.\n" +
                "What do you do now?");
        resetButtons();
        JButton btn1 = erstelleButton("Walk past quietly 👣", e -> checkpoint4Treasure());
        JButton btn2 = erstelleButton("Run loudly 🏃", e -> {
            animateText("🚨 He hears your heavy footsteps and fires blindly in your direction—a hit!");
            triggerGameOver();
        });
        choicePanel.add(btn1);
        choicePanel.add(btn2);
    }
    private void checkpoint4Treasure() {
        checkpoint = 4;
        ladeBild("treasure_room.jpg");
        animateText("You slip past him silently, like a ghost.\n\n" +
                "--- 🏁 CHECKPOINT 4: THE TREASURY ---\n" +
                "You are standing in the Galerie d'Apollon. Before you, Le Régent and Le Sancy are sparkling!\n" +
                "But red laser beams guard them. How do you proceed?");
        resetButtons();
        JButton btn1 = erstelleButton("Shatter display glass 🔪", e -> {
            animateText("🚨 The glass has alarm sensors! Iron bars drop down and trap you.");
            triggerGameOver();
        });
        JButton btn2 = erstelleButton("Disable lasers via hacking 💻", e -> checkpoint4LasersOut());
        choicePanel.add(btn1);
        choicePanel.add(btn2);
    }
    private void checkpoint4LasersOut() {
        ladeBild("lasers_off.jpg");
        animateText("The lasers go out! A timer ticks away relentlessly: 30... 29... 28...\n" +
                "Unlock the display case!");
        resetButtons();
        JButton btnLockpick = erstelleButton("Use Lockpicks 🔑", e -> {
            if (inventar.containsItem("lockpicks")) {
                checkpoint4Signature();
            } else {
                animateText("🚨 Wrong tool or no tool! Time is running out; the lasers are frying your gear.");
                triggerGameOver();
            }
        });
        choicePanel.add(btnLockpick);
    }
    private void checkpoint4Signature() {
        ladeBild("alarm.jpg");
        animateText("Click. The lock pops open! The diamonds land in the duffel bag.\n" +
                "The alarm sirens start wailing (weight sensor)! You have 5 seconds left.\n" +
                "Do you leave your signature?");
        resetButtons();
        JButton btn1 = erstelleButton("No time! Run to escape window", e -> triggerVictory(false));
        JButton btn2 = erstelleButton("Use Spraypaint 🫟", e -> {
            if (inventar.containsItem("spraypaint")) {
                triggerVictory(true);
            } else {
                animateText("You wanted to, but you lost your spray can!");
                triggerVictory(false);
            }
        });
        choicePanel.add(btn1);
        choicePanel.add(btn2);
    }
    private void triggerVictory(boolean withSignature) {
        ladeBild("victory.jpg");
        String endingText = "";
        if (withSignature) {
            endingText = "Shhh! A large, red smiley face adorns the empty display case. Gotta have style!\n\n";
        } else {
            endingText = "Why did you chicken out now? That's always your Trademark. I'm very dissapointed ☹️😤\n\n";
        }
        endingText += "🏆 😏😜--- MISSION SUCCESSFUL ---🤑🥴 🏆\n" +
                "You jump through the window onto the escape roof. Down below, the police search in vain.\n" +
                "Paris is asleep, but Viki remains the undisputed number one in the world!";
        animateText(endingText);
        resetButtons();
        JButton btnExit = erstelleButton("Close Game", e -> System.exit(0));
        choicePanel.add(btnExit);
    }
    private void triggerGameOver() {
// Ein roter "Game Over"-Bildschirmeffekt im Textfeld
        storyTextArea.setForeground(Color.RED);
        storyTextArea.append("\n\n🛑 --- GAME OVER --- 🛑\nYou got caught or made a mistake.");
        resetButtons();
        JButton btnRetry = erstelleButton("Continue from last Checkpoint (" + checkpoint + ")", e -> {
            storyTextArea.setForeground(new Color(223, 115, 255)); // Zurück zu Grün
            if (checkpoint == 1) checkpoint1Museum();
            else if (checkpoint == 2) checkpoint2Gallery();
            else if (checkpoint == 3) checkpoint3Security();
            else if (checkpoint == 4) checkpoint4Treasure();
        });
        JButton btnEnd = erstelleButton("End Game", e -> System.exit(0));
        choicePanel.add(btnRetry);
        choicePanel.add(btnEnd);
    }
}
