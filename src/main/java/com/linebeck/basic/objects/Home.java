package com.linebeck.basic.objects;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("Home")
public class Home implements ConfigurationSerializable {

    private final String name;
    public String getName() {
        return name;
    }

    private final Location location;
    public Location getLocation() {
        return location;
    }

    public Home(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Home{" +
                "name='" + name + '\'' +
                ", location=" + location +
                '}';
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("Location", location);
        return map;
    }

    public static Home deserialize(Map<String, Object> map) {
        String name = (String) map.get("name");
        Location location = (Location) map.get("Location");
        return new Home(name, location);
    }
}