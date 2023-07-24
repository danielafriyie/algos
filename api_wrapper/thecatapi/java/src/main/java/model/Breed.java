package model;

import java.util.Map;

public class Breed extends Model {
    private final String name;
    private final String weight;
    private final String height;
    private final String lifeSpan;
    private final String bredFor;
    private final String breedGroup;

    public Breed(int id, Map<String, Object> data) {
        super(id, data);
        this.name = String.valueOf(data.get("name"));
        this.weight = String.valueOf(data.get("weight"));
        this.height = String.valueOf(data.get("height"));
        this.lifeSpan = String.valueOf(data.get("life_span"));
        this.bredFor = String.valueOf(data.get("bred_for"));
        this.breedGroup = String.valueOf(data.get("breed_group"));
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getBredFor() {
        return bredFor;
    }

    public String getBreedGroup() {
        return breedGroup;
    }
}
