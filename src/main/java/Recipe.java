import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// Annotation-Library von Jackson (der JSON-Bibliothek) erlaubt,
// Regeln für das Umwandeln von JSON in Java zu definieren.

@JsonIgnoreProperties(ignoreUnknown = true) // Felder ignorieren, die nicht hier definiert wurden

public class Recipe {
    public int id;
    public String title;
    public String image; // Diese Felder müssen im JSON enthalten sein
    // In vielen Projekten macht man sie lieber private mit get- und set-Methoden,
    // aber Jackson kann mit public-Feldern auch direkt umgehen (vor allem in einfachen Projekten).

    //  Für Rezepte, bei denen Zutaten abgeglichen werden sollen
    public int usedIngredientCount;
    public int missedIngredientCount;

    public List<Ingredient> usedIngredients;
    public List<Ingredient> missedIngredients;

    @Override // Damit kommt eine Fehlermeldung, wenn man sich vertippt hat
    public String toString() {
        return "Rezept: " + title + " (ID: " + id + ")\n" +
                "Verwendet: " + usedIngredients + "\n" +
                "Fehlend: " + missedIngredients + "\n";
    }
}