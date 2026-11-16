package day5;

// Importiert ArrayList, eine Liste, deren Größe sich ändern kann.
import java.util.ArrayList;

// Importiert den allgemeinen List-Typ für die Gegenstände.
import java.util.List;

public class Inventory {

    // Speichert die Gegenstände. private verhindert direkten Zugriff von außen.
    private List<String> items;

    // Wird automatisch aufgerufen, wenn ein neues Inventory-Objekt erstellt wird.
    public Inventory() {

        // Erstellt die Liste, in der die Gegenstände gespeichert werden.
        items = new ArrayList<>();

        // Fügt die Gegenstände hinzu, die zu Spielbeginn vorhanden sind.
        items.add("Spraypaint 🫟");
        items.add("gloves 🧤");
        items.add("lockpicks 🔑");
        items.add("screwdriver 🪛");
        items.add("glass cutter 🔪");
    }

    // Getter: Gibt die Liste der Gegenstände zurück.
    public List<String> getItems() {
        return items;
    }

    // Setter: Ersetzt die aktuelle Liste durch die übergebene Liste.
    public void setItems(List<String> items) {

        // this.items meint das Feld des Objekts;
        // items ohne this ist der Parameter, der übergeben wurde.
        this.items = items;
    }

    // Fügt einen neuen Gegenstand am Ende der Liste hinzu.
    public void addItem(String item) {
        items.add(item);
    }

    // Gibt alle Gegenstände mit einer auswählbaren Nummer aus.
    public void showInventory() {
        System.out.println("\n🎒 [YOUR DUFFELBAG] Currently contains:");

        // Durchläuft die Liste von ihrem ersten bis zu ihrem letzten Eintrag.
        for (int i = 0; i < items.size(); i++) {

            // Die Anzeige beginnt bei 1; der tatsächliche Listenindex beginnt bei 0.
            System.out.println("   [" + (i + 1) + "] " + items.get(i));
        }
    }

    // Sucht einen Gegenstand anhand seiner Nummer oder seines Namens.
    public String findItem(String input) {

        // Entfernt Leerzeichen am Anfang und Ende der Eingabe.
        input = input.trim();

        // Versucht zuerst, die Eingabe als Nummer zu lesen.
        try {
            int id = Integer.parseInt(input);

            // Prüft, ob die Nummer zu einem Eintrag in der Liste passt.
            if (id >= 1 && id <= items.size()) {

                // Zieht 1 ab, weil Listen bei Index 0 beginnen.
                return items.get(id - 1);
            }

            // Die Nummer liegt außerhalb des gültigen Bereichs.
            return null;

        } catch (NumberFormatException e) {

            // Wenn die Eingabe keine Zahl ist, wird sie als Name behandelt.
            for (String item : items) {

                // Holt den Namen ohne das Emoji am Ende.
                String itemName = getItemName(item);

                // Vergleicht die Namen ohne Groß-/Kleinschreibung zu beachten.
                if (itemName.equalsIgnoreCase(input)) {
                    return item;
                }
            }

            // Kein passender Name wurde in der Liste gefunden.
            return null;
        }
    }

    // Prüft, ob ein Gegenstand mit dieser Nummer oder diesem Namen existiert.
    public boolean containsItem(String input) {

        // findItem liefert null, wenn es keinen passenden Gegenstand gibt.
        return findItem(input) != null;
    }

    // Gibt den Gegenstandsnamen ohne das letzte Wort, hier das Emoji, zurück.
    private String getItemName(String item) {

        // Sucht die Position des letzten Leerzeichens.
        int emojiPosition = item.lastIndexOf(" ");

        // Ohne Leerzeichen gibt es nichts abzutrennen.
        if (emojiPosition == -1) {
            return item;
        }

        // Gibt den Teil vor dem letzten Leerzeichen zurück.
        return item.substring(0, emojiPosition);
    }
}