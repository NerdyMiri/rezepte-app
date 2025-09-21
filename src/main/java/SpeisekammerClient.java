import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import okhttp3.Response;

public class SpeisekammerClient {
    static Dotenv dotenv = Dotenv.load(); // .env wird automatisch gefunden

    private static final String API_TOKEN1 = dotenv.get("API_TOKEN");
    private static final String BASE_URL = "https://api.speisekammer.app";

    public static String getCommunities() {


        Request request = new Request.Builder()
                .url(BASE_URL + "/communities") // Um auf verschiedene Daten zuzugreifen, werden verschiedene
                                                // Endpunkte drangehängt: /communities, /locations, /stocks.
                .addHeader("Authorization", "Bearer " + API_TOKEN1)
                // Bei Spoonacular kommt der API-Key direkt in die URL, was einfacher und schneller ist.
                // Hier wird der Token im HTTP-Header geschickt, nicht in der URL.
                // Sicherer bei Benutzerkonten und Rollenrechten, da es eingeschränkt werden kann.
                .build();

        try (Response response = HttpClientProvider.CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string(); // JSON-Antwort zurückgeben
            } else {
                System.out.println("Fehler beim Abrufen der Communities: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String getStorageLocations(String communityId) {


        String url = BASE_URL + "/communities/" + communityId + "/storage-locations";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + API_TOKEN1)
                .build();

        try (Response response = HttpClientProvider.CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                System.out.println("Fehler beim Abrufen der Lagerorte: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String getStock(String communityId, String storageLocationId) {


        String url = BASE_URL + "/stock/" + communityId + "/" + storageLocationId;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + API_TOKEN1)
                .build();

        try (Response response = HttpClientProvider.CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                System.out.println("Fehler beim Abrufen der Produkte: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}