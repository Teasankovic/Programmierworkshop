package DesktopApp;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class MainPartFunction {

    private boolean isLightMode = false;

    public boolean isLightMode() {
        return isLightMode;
    }

    public void toggleTheme() {
        isLightMode = !isLightMode;
    }

    // Farb-Definitionen exakt nach deinem CSS
    public Color getBgDark() {
        return isLightMode ? Color.decode("#f5ebe0") : Color.decode("#230c0f");
    }

    public Color getAccentOrange() {
        return isLightMode ? Color.decode("#b84a14") : Color.decode("#de6b21");
    }

    public Color getAccentYellow() {
        return isLightMode ? Color.decode("#d30000") : Color.decode("#f9a01b");
    }

    public Color getTextMain() {
        return isLightMode ? Color.decode("#36171b") : Color.decode("#fce8cd");
    }

    public Color getCardBg() {
        return isLightMode ? Color.decode("#e3d5ca") : Color.decode("#36171b");
    }

    public Color getPreviewBg() {
        return isLightMode ? Color.decode("#d5bdaf") : Color.decode("#140507");
    }

    public Color getGradientStart() {
        return isLightMode ? Color.decode("#d35400") : Color.decode("#f39c12");
    }

    public Color getGradientEnd() {
        return isLightMode ? Color.decode("#f39c12") : Color.decode("#d35400");
    }

    public Color getTitleColor() {
        return isLightMode ? Color.decode("#36171b") : Color.decode("#230c0f");
    }

    public Color getTitleShadow() {
        return isLightMode ? Color.decode("#fbd38d") : Color.decode("#f9a01b");
    }

    /**
     * Startet das ausgewählte Spiel aus dem Ordner "games"
     */
    public void launchGame(String jarName) {
        File gameFile = new File(jarName);

        if (!gameFile.exists()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Die Datei '" + gameFile.getAbsolutePath() + "' wurde nicht gefunden.\n" +
                            "Bitte lege einen Ordner 'games' im Projektordner an und packe deine .jar-Datei hinein.",
                    "Spiel nicht gefunden",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", gameFile.getAbsolutePath());
            pb.directory(gameFile.getParentFile()); // <-- Setzt das Arbeitsverzeichnis auf den Ordner, in dem die .jar liegt!
            pb.inheritIO();
            pb.start();
            System.out.println("Starte Spiel: " + jarName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Fehler beim Starten von " + jarName + ":\n" + e.getMessage(),
                    "Startfehler",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
