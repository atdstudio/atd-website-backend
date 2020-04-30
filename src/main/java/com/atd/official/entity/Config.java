package com.atd.official.entity;

public class Config {

    String Id;
    String Name;
    String Value;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return "Config{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}
