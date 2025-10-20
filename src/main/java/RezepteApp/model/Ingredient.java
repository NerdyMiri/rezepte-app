package RezepteApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient {
    public String name;

    @Override
    public String toString() {
        return name;
    }
}