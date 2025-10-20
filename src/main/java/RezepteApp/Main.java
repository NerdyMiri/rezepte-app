package RezepteApp;

import RezepteApp.client.SpeisekammerClient;
import RezepteApp.client.SpoonacularClient;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // RezepteApp.client.SpoonacularClient.testVerbindung();

        List<String> zutatenSpeisekammer = List.of("Paprika", "Reis", "Tofu");
        SpoonacularClient.findByIngredients(zutatenSpeisekammer);

        String json = SpeisekammerClient.getCommunities();
        // System.out.println(json); // Zeigt die Community-Infos

        Dotenv dotenv = Dotenv.load();

        String ID = dotenv.get("communityId");


        String json1 = SpeisekammerClient.getStorageLocations(ID);

        // System.out.println(json1); // Zeigt die Lagerorte
    }

}

