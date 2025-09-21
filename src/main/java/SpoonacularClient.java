import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import okhttp3.Response;
// Wird verwendet, um HTTP-Anfragen zu schicken und Antworten zu empfangen.

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
// Wird verwendet, um JSON-Daten in Java-Objekte umzuwandeln (Deserialisierung).

import java.util.List;

public class SpoonacularClient {

    static Dotenv dotenv = Dotenv.load(); // .env wird automatisch gefunden

    private static final String API_KEY1 = dotenv.get("API_KEY"); // Hole den Key;
    // Ein API-Key sollte nicht öffentlich sein
    // im Produktivcode würde man den in einer .env-Datei o.ä. speichern.
    private static final String BASE_URL = "https://api.spoonacular.com";
    // Die Basis-URL für die Spoonacular API, um nicht immer den gesamten Pfad schreiben zu müssen.

    public static void testVerbindung() { // soll von main abrufbar sein daher public


        String url = BASE_URL + "/recipes/random?number=1&apiKey=" + API_KEY1;
        // Die Struktur kommt aus der API-Dokumentation von Spoonacular.

        Request request = new Request.Builder() // Hier wird eine GET-Anfrage gebaut
                .url(url)                       // Struktur aus der Dokumentation von OkHttp
                .build();

        try (Response response = HttpClientProvider.CLIENT.newCall(request).execute()) {
            // try(...) sorgt dafür, dass die Antwort automatisch geschlossen wird.
            // ohne müsste response.close(); ans Ende
            if (response.isSuccessful()) { // Methode aus OkHttp prüft den Statuscode
                String json = response.body().string();
                // liest den JSON-Text aus der Antwort als String

                ObjectMapper mapper = new ObjectMapper();
                // Klasse von Jackson (quasi ein Übersetzer zwischen JSON und Java)

                // Erst das gesamte Objekt einlesen
                JsonNode root = mapper.readTree(json);
                // JSON-Text in ein JsonNode-Baumobjekt verwandeln

                // Dann das "recipes"-Array(ein Ast) rausholen
                JsonNode recipesNode = root.get("recipes");

                // Dann das Array in eine Liste von Recipe-Objekten umwandeln
                List<Recipe> rezepte = mapper.readValue(recipesNode.toString(), new TypeReference<>() {});
                // Die Klasse Recipe muss entsprechend definiert sein (mit Feldern wie title, ingredients, etc.).

                for (Recipe r : rezepte) {
                    System.out.println(r); // Gibt jedes Rezept-Objekt in der Konsole aus
                    // toString()-Methode in der Recipe-Klasse implementieren, um sinnvolle Ausgaben zu bekommen.
                }
            } else {
                System.out.println("Fehler: " + response.code());   // Wenn die Antwort nicht erfolgreich war
                                                                    // wird der HTTP-Statuscode ausgegeben
            }
        } catch (Exception e) {
            e.printStackTrace(); // Drucke den Fehlerbericht auf die Konsole
        }
    }
    public static void findByIngredients(List<String> zutatenListe) {


        String zutaten = String.join(",", zutatenListe);   // macht aus der Liste einen Kommaseparierten String
                                                                    // weil die API die Zutaten so erwartet
        String url = BASE_URL + "/recipes/findByIngredients?ingredients=" + zutaten + "&number=1&apiKey=" + API_KEY1;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = HttpClientProvider.CLIENT.newCall(request).execute()) { // Anfrage
            if (response.isSuccessful()) {
                String json = response.body().string(); // Die Antwort von findByIngredients ist ein Array von Rezepten

                ObjectMapper mapper = new ObjectMapper(); // Jackson wird benutzt, um in eine Liste zu parsen.
                List<Recipe> rezepte = mapper.readValue(json, new TypeReference<>() {});

                for (Recipe r : rezepte) {
                    System.out.println(r);
                }
            } else {
                System.out.println("Fehler: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
