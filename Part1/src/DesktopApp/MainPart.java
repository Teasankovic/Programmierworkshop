package DesktopApp;


import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainPart extends JPanel {

    private final MainPartFunction functions;
    private JButton themeToggleBtn;
    private JPanel headerPanel;
    private JLabel mainTitleShadow;
    private JLabel mainTitle;
    private JLabel subtitle;
    private JPanel centerWrapper;
    private JPanel gamesSection;
    private JLabel sectionTitle;
    private JPanel footerPanel;
    private JLabel footerLabel;

    // Liste zum Aktualisieren der Karten beim Theme-Wechsel
    private final List<GameCardComponents> cards = new ArrayList<>();

    public MainPart() {
        this.functions = new MainPartFunction();
        setLayout(new BorderLayout());

        initComponents();
        applyTheme(); // Initiales Dark-Theme anwenden
    }

    private void initComponents() {
        // 1. HEADER MIT FIREWATCH-FARBVERLAUF
        headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                // Farbverlauf von Orange/Gelb in den dunklen Hintergrund
                GradientPaint gp = new GradientPaint(
                        0, 0, functions.getGradientStart(),
                        0, h, functions.getBgDark()
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, 240));
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));


        // Light/Dark Mode Toggle oben rechts
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.setOpaque(false);
        themeToggleBtn = new JButton("☀ Light Mode");
        themeToggleBtn.setFont(new Font("Serif", Font.BOLD, 14));
        themeToggleBtn.setFocusPainted(false);
        themeToggleBtn.setContentAreaFilled(false);
        themeToggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        themeToggleBtn.addActionListener(e -> {
            functions.toggleTheme();
            themeToggleBtn.setText(functions.isLightMode() ? "☾ Dark Mode" : "☀ Light Mode");
            applyTheme();
        });
        topBar.add(themeToggleBtn);
        headerPanel.add(topBar, BorderLayout.NORTH);

        // Titel & Untertitel mit Schatten-Effekt (LayeredPane für exakten 4px Versatz)
        JLayeredPane titlePane = new JLayeredPane();
        titlePane.setPreferredSize(new Dimension(900, 140));

        mainTitle = new JLabel("TEA'S SMALL ARCADE", SwingConstants.CENTER);
        mainTitle.setFont(new Font("Serif", Font.BOLD, 54));
        mainTitle.setBounds(0, 10, 900, 70);

        mainTitleShadow = new JLabel("TEA'S SMALL ARCADE", SwingConstants.CENTER);
        mainTitleShadow.setFont(new Font("Serif", Font.BOLD, 54));
        mainTitleShadow.setBounds(4, 14, 900, 70); // 4px Schatten-Versatz wie im CSS

        subtitle = new JLabel("CLASSIC GAMES YOU CAN PLAY", SwingConstants.CENTER);
        subtitle.setFont(new Font("Serif", Font.BOLD, 16));
        subtitle.setBounds(0, 85, 900, 30);

        titlePane.add(mainTitleShadow, Integer.valueOf(1));
        titlePane.add(mainTitle, Integer.valueOf(2));
        titlePane.add(subtitle, Integer.valueOf(2));

        JPanel titleCenterBox = new JPanel(new GridBagLayout());
        titleCenterBox.setOpaque(false);
        titleCenterBox.add(titlePane);
        headerPanel.add(titleCenterBox, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // 2. HAUPTBEREICH MIT DEN 3 SPIEL-KARTEN
        centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBorder(new EmptyBorder(10, 60, 30, 60));

        gamesSection = new JPanel(new BorderLayout(0, 30));
        gamesSection.setPreferredSize(new Dimension(1150, 480));

        sectionTitle = new JLabel("CHOOSE YOUR GAME", SwingConstants.CENTER);
        sectionTitle.setFont(new Font("Serif", Font.BOLD, 26));
        gamesSection.add(sectionTitle, BorderLayout.NORTH);

        // 3 Spalten nebeneinander für deine 3 Spiele
        JPanel gamesGrid = new JPanel(new GridLayout(1, 3, 35, 0));
        gamesGrid.setOpaque(false);

        gamesGrid.add(createGameCard(
                "🦤",
                "FLAPPY BIRD",
                "Dodge the pipes and beat your own highscore!",
                "../FlappyBird.jar"
        ));

        gamesGrid.add(createGameCard(
                "🃏",
                "BLACKJACK",
                "Challenge the Dealer. Do you come closer to 21?",
                "../Blackjack.jar"
        ));

        gamesGrid.add(createGameCard(
                "🐍",
                "SNAKE",
                "Eat the apples, grow infinitely, but don't crash into the wall!",
                "../Snake.jar"
        ));

        gamesSection.add(gamesGrid, BorderLayout.CENTER);
        centerWrapper.add(gamesSection);
        add(centerWrapper, BorderLayout.CENTER);

        // 3. FOOTER UNTEN
        footerPanel = new JPanel(new GridBagLayout());
        footerPanel.setPreferredSize(new Dimension(0, 70));
        footerLabel = new JLabel("TEA'S SMALL ARCADE");
        footerLabel.setFont(new Font("Serif", Font.BOLD, 15));
        footerPanel.add(footerLabel);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createGameCard(String icon, String title, String description, String jarName) {
        GameCardComponents comp = new GameCardComponents();

        comp.cardPanel = new JPanel(new BorderLayout());

        // Vorschaubox oben mit Emoji/Icon
        comp.previewBox = new JPanel(new GridBagLayout());
        comp.previewBox.setPreferredSize(new Dimension(0, 170));
        comp.iconLabel = new JLabel(icon);
        comp.iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        comp.previewBox.add(comp.iconLabel);

        // Info-Bereich unten
        comp.infoPanel = new JPanel(new BorderLayout(0, 10));
        comp.infoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        comp.titleLabel = new JLabel(title, SwingConstants.CENTER);
        comp.titleLabel.setFont(new Font("Serif", Font.BOLD, 20));

        // HTML-Wrapping für sauberen mehrzeiligen Text
        comp.descLabel = new JLabel("<html><div style='text-align: center;'>" + description + "</div></html>", SwingConstants.CENTER);
        comp.descLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        comp.playBtn = new JButton("PLAY NOW");
        comp.playBtn.setFont(new Font("Serif", Font.BOLD, 15));
        comp.playBtn.setFocusPainted(false);
        comp.playBtn.setBorder(new EmptyBorder(12, 20, 12, 20));
        comp.playBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Klick startet das Spiel über MainPartFunction
        comp.playBtn.addActionListener(e -> functions.launchGame(jarName));

        // Hover-Effekt wie im CSS (.play-btn:hover)
        comp.playBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                comp.playBtn.setBackground(functions.getAccentYellow());
                comp.playBtn.setForeground(Color.decode("#230c0f"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                comp.playBtn.setBackground(functions.getAccentOrange());
                comp.playBtn.setForeground(Color.WHITE);
            }
        });

        comp.infoPanel.add(comp.titleLabel, BorderLayout.NORTH);
        comp.infoPanel.add(comp.descLabel, BorderLayout.CENTER);
        comp.infoPanel.add(comp.playBtn, BorderLayout.SOUTH);

        comp.cardPanel.add(comp.previewBox, BorderLayout.NORTH);
        comp.cardPanel.add(comp.infoPanel, BorderLayout.CENTER);

        cards.add(comp);
        return comp.cardPanel;
    }

    /**
     * Wendet alle Farben für Dark- oder Light-Mode live auf das gesamte UI an
     */
    private void applyTheme() {
        Color bgDark = functions.getBgDark();
        Color accentOrange = functions.getAccentOrange();
        Color textMain = functions.getTextMain();
        Color cardBg = functions.getCardBg();
        Color previewBg = functions.getPreviewBg();
        Color titleColor = functions.getTitleColor();
        Color titleShadow = functions.getTitleShadow();

        setBackground(bgDark);
        centerWrapper.setBackground(bgDark);

        // Toggle-Button Styling
        themeToggleBtn.setForeground(titleColor);
        themeToggleBtn.setBorder(new CompoundBorder(
                new LineBorder(titleColor, 2),
                new EmptyBorder(6, 14, 6, 14)
        ));

        // Header-Texte
        mainTitle.setForeground(titleColor);
        mainTitleShadow.setForeground(titleShadow);
        subtitle.setForeground(titleColor);
        headerPanel.repaint();

        // Spiele-Container
        gamesSection.setBackground(cardBg);
        gamesSection.setBorder(new CompoundBorder(
                new LineBorder(accentOrange, 2),
                new EmptyBorder(30, 35, 35, 35)
        ));
        sectionTitle.setForeground(accentOrange);

        // Alle 3 Spiel-Karten einfärben
        for (GameCardComponents c : cards) {
            c.cardPanel.setBackground(bgDark);
            c.cardPanel.setBorder(new LineBorder(accentOrange, 2));

            c.previewBox.setBackground(previewBg);
            c.previewBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, accentOrange));

            c.infoPanel.setBackground(bgDark);
            c.titleLabel.setForeground(accentOrange);
            c.descLabel.setForeground(textMain);

            c.playBtn.setBackground(accentOrange);
            c.playBtn.setForeground(Color.WHITE);
        }
// Footer
        footerPanel.setBackground(previewBg);
        footerPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, accentOrange));
        footerLabel.setForeground(accentOrange);
    }
    private static class GameCardComponents {
        JPanel cardPanel;
        JPanel previewBox;
        JLabel iconLabel;
        JPanel infoPanel;
        JLabel titleLabel;
        JLabel descLabel;
        JButton playBtn;
    }
    // Main-Methode: Startet direkt im Vollbildmodus auf deinem HP-Laptop
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tea's small Arcade");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// Zieht das Fenster automatisch über den gesamten Bildschirm des Laptops
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setMinimumSize(new Dimension(1000, 700));
            frame.setContentPane(new MainPart());
            frame.setVisible(true);
        });
    }
}
