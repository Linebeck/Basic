package com.linebeck.basic.data;

import com.linebeck.basic.internal.Main;
import com.linebeck.basic.objects.Home;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeData {

    // Individual User's Home Folder.
    private File getHomeFolder(UUID uuid) {
        var homeFolder = new File(BasicPlayerData.playerDataFolder + File.separator + uuid.toString() + File.separator + "homes");
        if (!homeFolder.exists()) {
            if(!homeFolder.mkdirs()) return null;
        }
        return homeFolder;
    }

    // Get Home Object.
    public Home getHome(UUID uuid, String name) {
        var homeFolder = getHomeFolder(uuid);
        if(homeFolder == null) return null;

        var fileNameList = homeFolder.list();
        if(fileNameList == null) return null;

        for(String fileName : fileNameList) {
            var checkName = fileName.replace(".yml", "");

            if(checkName.equalsIgnoreCase(name)) {
                var homeFile = new File(homeFolder, fileName);
                var yamlConfiguration = YamlConfiguration.loadConfiguration(homeFile);
                return (Home) yamlConfiguration.get("Data");
            }
        }
        return null;
    }

    // Get List of Homes.
    public List<Home> getHomes(UUID uuid) {
        var homeFolder = getHomeFolder(uuid);
        if(homeFolder == null) return null;

        Main.getInstance().getLogger().info("Retrieving homes for " + uuid + "!");

        List<Home> homes = new ArrayList<>();

        var fileList = homeFolder.listFiles();
        if(fileList == null) return homes;

        for(var homeFile : fileList) {
            var yamlConfiguration = YamlConfiguration.loadConfiguration(homeFile);
            Home home = (Home) yamlConfiguration.get("Data");
            if(home != null) {
                homes.add(home);
            }
        }
        return homes;
    }

    // Save Home Data.
    public boolean saveHome(UUID uuid, Home home) {
        var homeFolder = getHomeFolder(uuid);
        if(homeFolder == null) return false;

        var homeFile = new File(homeFolder, home.getName().toLowerCase() + ".yml");

        try {
            if(!homeFile.exists()) {
                if(!homeFile.createNewFile()) return false;
            }

            var yamlConfiguration = YamlConfiguration.loadConfiguration(homeFile);
            yamlConfiguration.set("Data", home);

            Main.getInstance().getLogger().info("Saved " + home.getName() + " for " + uuid + "!");

            yamlConfiguration.save(homeFile);

            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    // Delete Home Data.
    public boolean deleteHome(UUID uuid, String name) {
        var homeFolder = getHomeFolder(uuid);
        if(homeFolder == null) return false;

        var homeFile = new File(homeFolder, name.toLowerCase() + ".yml");
        if(!homeFile.exists()) return false;
        return homeFile.delete();
    }
}