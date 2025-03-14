package com.linebeck.basic.data;

import com.linebeck.basic.internal.Main;
import com.linebeck.basic.objects.BasicPlayer;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class BasicPlayerData {

    public static final File playerDataFolder = new File(Main.getInstance().getDataFolder() + File.separator + "playerdata");

    private boolean createPlayerDataFolder() {
        if(!playerDataFolder.exists()) {
            return playerDataFolder.mkdir();
        }
        return false;
    }

    // Create Player's Data Folder & Files.
    public void createPlayerFolder(BasicPlayer basicPlayer) {
        if(createPlayerDataFolder()) {
            Main.getInstance().getLogger().info("Creating player data folder...");
        }

        var playerFolder = new File(playerDataFolder + File.separator + basicPlayer.getUUID().toString());

        if(!playerFolder.exists()) {
            playerFolder.mkdir();
        }

        try {
            var playerDataFile = new File(playerFolder, "data.yml");
            if(!playerDataFile.createNewFile()) return;

            saveBasicPlayer(basicPlayer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // Check to make sure player has all data.
    public boolean hasData(UUID uuid) {
        var playerFolder = new File(playerDataFolder + File.separator + uuid.toString());
        var folderExists = playerFolder.exists();

        var playerDataFile = new File(playerFolder, "data.yml");
        var fileExists = playerDataFile.exists();

        return (folderExists && fileExists);
    }

    public BasicPlayer getBasicPlayer(UUID uuid) {
        var playerDataFile = new File(playerDataFolder + File.separator + uuid.toString(), "data.yml");
        if(playerDataFile.exists()) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(playerDataFile);
            return (BasicPlayer) yamlConfiguration.get("Data");
        }
        return null;
    }

    // Save Player's BasicPlayer.
    public void saveBasicPlayer(BasicPlayer basicPlayer) {
        var playerDataFile = new File(playerDataFolder + File.separator + basicPlayer.getUUID(), "data.yml");

        if(playerDataFile.exists()) {
            try {
                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(playerDataFile);
                yamlConfiguration.set("Data", basicPlayer);
                yamlConfiguration.save(playerDataFile);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    // Set Previous Location.
    public void setPreviousLocation(UUID uuid, Location location) {
        var basicPlayer = getBasicPlayer(uuid);
        if(basicPlayer == null) return;
        basicPlayer.setPreviousLocation(location);
        saveBasicPlayer(basicPlayer);
    }
}