package models;

public class PartsModel {

    private String key;
    private int value;

    public PartsModel(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
