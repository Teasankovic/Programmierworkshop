package day5;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<String> items;

    // Konstruktor
    public Inventory() {
        items = new ArrayList<>();

        items.add("Spraypaint 🫟");
        items.add("gloves 🧤");
        items.add("lockpicks 🔑");
        items.add("screwdriver 🪛");
        items.add("glass cutter 🔪");
    }

    // Getter
    public List<String> getItems() {
        return items;
    }

    // Setter
    public void setItems(List<String> items) {
        this.items = items;
    }

    // Neues Item hinzufügen
    public void addItem(String item) {
        items.add(item);
    }

    // Item entfernen
    public boolean removeItem(String input) {
        String item = findItem(input);

        if (item != null) {
            items.remove(item);
            return true;
        }

        return false;
    }

    // Inventar anzeigen
    public void showInventory() {
        System.out.println("\n🎒 [YOUR DUFFELBAG] Currently contains:");

        for (int i = 0; i < items.size(); i++) {
            System.out.println("   [" + (i + 1) + "] " + items.get(i));
        }
    }

    // Item über Nummer oder Namen suchen
    public String findItem(String input) {
        input = input.trim();

        // Auswahl über die Inventar-ID
        try {
            int id = Integer.parseInt(input);

            if (id >= 1 && id <= items.size()) {
                return items.get(id - 1);
            }

            return null;

        } catch (NumberFormatException e) {
            // Auswahl über den Namen
            for (String item : items) {
                String itemName = getItemName(item);

                if (itemName.equalsIgnoreCase(input)) {
                    return item;
                }
            }

            return null;
        }
    }

    // Prüft, ob ein bestimmtes Item ausgewählt wurde
    public boolean containsItem(String input) {
        return findItem(input) != null;
    }

    // Holt nur den Namen ohne Emoji
    private String getItemName(String item) {
        int emojiPosition = item.lastIndexOf(" ");

        if (emojiPosition == -1) {
            return item;
        }

        return item.substring(0, emojiPosition);
    }
}