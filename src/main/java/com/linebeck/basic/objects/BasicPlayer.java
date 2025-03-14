package com.linebeck.basic.objects;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("BasicPlayer")
public class BasicPlayer implements ConfigurationSerializable {

    private final UUID uuid;
    public UUID getUUID() {
        return uuid;
    }

    private final String name;
    public String getName() {
        return name;
    }

    private final String ipAddress;
    public String getIPAddress() {
        return ipAddress;
    }

    private Location logoutLocation;
    public Location getLogoutLocation() { return logoutLocation; }
    public void setLogoutLocation(Location location) {
        this.logoutLocation = location;
    }

    private Location previousLocation;
    public Location getPreviousLocation() { return previousLocation; }
    public void setPreviousLocation(Location location) {
        this.previousLocation = location;
    }

    public BasicPlayer(
            UUID uuid,
            String name,
            String ipAddress,
            @Nullable Location previousLocation,
            @Nullable Location logoutLocation) {
        this.uuid = uuid;
        this.name = name;
        this.ipAddress = ipAddress;
        this.previousLocation = previousLocation;
        this.logoutLocation = logoutLocation;
    }

    @Override
    public String toString() {
        return "BasicPlayer{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", logoutLocation=" + (logoutLocation != null ? logoutLocation.toString() : "null") +
                ", previousLocation=" + (previousLocation != null ? previousLocation.toString() : "null") +
                '}';
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", uuid.toString());
        map.put("Account-Name", name);
        map.put("IP-Address", ipAddress);
        map.put("Previous-Location", previousLocation);
        map.put("Logout-Location", logoutLocation);
        return map;
    }

    public static BasicPlayer deserialize(Map<String, Object> map) {
        String uuid = (String) map.get("uuid");
        String name = (String) map.get("Account-Name");
        String ipAddress = (String) map.get("IP-Address");

        Location previousLocation = map.get("Previous-Location") instanceof Location
                ? (Location) map.get("Previous-Location")
                : null;


        Location logoutLocation = map.get("Logout-Location") instanceof Location
                ? (Location) map.get("Logout-Location")
                : null;

        return new BasicPlayer(UUID.fromString(uuid), name, ipAddress, previousLocation, logoutLocation);
    }
}