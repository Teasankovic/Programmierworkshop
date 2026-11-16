package DesktopApp;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class AppFunction {

    // Speichert, ob gerade der helle Modus aktiv ist
    private boolean isLightMode = false;

    public boolean isLightMode() {
        return isLightMode;
    }

    // Wechselt zwischen hellem und dunklem Modus
    public void toggleTheme() {
        isLightMode = !isLightMode;
    }

    // Gibt je nach Theme die Hintergrundfarbe zurück
    public Color getBgDark() {
        return isLightMode ? Color.decode("#f5ebe0") : Color.decode("#230c0f");
    }

    // Gibt die orange Akzentfarbe zurück
    public Color getAccentOrange() {
        return isLightMode ? Color.decode("#b84a14") : Color.decode("#de6b21");
    }

    // Gibt die gelbe Akzentfarbe zurück
    public Color getAccentYellow() {
        return isLightMode ? Color.decode("#d30000") : Color.decode("#f9a01b");
    }

    // Gibt die Haupttextfarbe zurück
    public Color getTextMain() {
        return isLightMode ? Color.decode("#36171b") : Color.decode("#fce8cd");
    }

    // Gibt die Hintergrundfarbe der Spiele-Karten zurück
    public Color getCardBg() {
        return isLightMode ? Color.decode("#e3d5ca") : Color.decode("#36171b");
    }

    // Gibt die Hintergrundfarbe der Vorschauflächen zurück
    public Color getPreviewBg() {
        return isLightMode ? Color.decode("#d5bdaf") : Color.decode("#140507");
    }

    // Gibt die Startfarbe des Farbverlaufs im Header zurück
    public Color getGradientStart() {
        return isLightMode ? Color.decode("#d35400") : Color.decode("#f39c12");
    }

    // Gibt die zweite Farbe des Farbverlaufs zurück
    public Color getGradientEnd() {
        return isLightMode ? Color.decode("#f39c12") : Color.decode("#d35400");
    }

    // Gibt die Farbe des Haupttitels zurück
    public Color getTitleColor() {
        return isLightMode ? Color.decode("#36171b") : Color.decode("#230c0f");
    }

    // Gibt die Farbe des Schattens unter dem Titel zurück
    public Color getTitleShadow() {
        return isLightMode ? Color.decode("#fbd38d") : Color.decode("#f9a01b");
    }

    // Startet die ausgewählte JAR-Datei
    public void launchGame(String jarName) {
        File gameFile = new File(jarName);

        // Zeigt eine Meldung an, wenn die JAR-Datei nicht gefunden wird
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
            // Startet die JAR-Datei mit Java
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", gameFile.getAbsolutePath());

            // Legt den Ordner der JAR-Datei als Arbeitsverzeichnis fest
            pb.directory(gameFile.getParentFile());

            // Übernimmt Ein- und Ausgaben des aktuellen Programms
            pb.inheritIO();

            pb.start();
            System.out.println("Starte Spiel: " + jarName);
        } catch (IOException e) {
            // Zeigt eine Fehlermeldung an, falls das Spiel nicht gestartet werden kann
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