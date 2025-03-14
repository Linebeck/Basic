package com.linebeck.basic.data;

import com.linebeck.basic.internal.Main;
import com.linebeck.basic.objects.Warp;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WarpData {

    // Create Warp Folder.
    private File getWarpFolder() {
        var warpFolder = new File(Main.getInstance().getDataFolder() + File.separator + "warps");
        if(!warpFolder.exists()) {
            if(!warpFolder.mkdir()) return null;
        }
        return warpFolder;
    }

    // Get Specific Warp.
    public Warp getWarp(String name) {
        var warpFolder = getWarpFolder();
        File warpFile = new File(warpFolder, name + ".yml");

        if(warpFile.exists()) {
            YamlConfiguration warpYaml = YamlConfiguration.loadConfiguration(warpFile);
            return (Warp) warpYaml.get("Data");
        } else {
            return null;
        }
    }

    // Gather list of Warps.
    public List<Warp> getWarps() {
        var warpFolder = getWarpFolder();
        if (warpFolder == null) return null;

        List<Warp> warps = new ArrayList<>();
        var fileList = warpFolder.listFiles();
        if (fileList == null) return warps;

        for (var warpFile : fileList) {
            var yamlConfiguration = YamlConfiguration.loadConfiguration(warpFile);
            Warp warp = (Warp) yamlConfiguration.get("Data");
            if (warp != null) {
                warps.add(warp);
            }
        }
        return warps;
    }

    // Save Warp.
    public boolean saveWarp(Warp warp) {
        var warpFolder = getWarpFolder();
        if(warpFolder == null) return false;

        var warpFile = new File(warpFolder, warp.getName().toLowerCase() + ".yml");

        try {
            if(!warpFile.exists()) {
                if(!warpFile.createNewFile()) return false;
            }
            var yamlConfiguration = YamlConfiguration.loadConfiguration(warpFile);
            yamlConfiguration.set("Data", warp);
            yamlConfiguration.save(warpFile);

            Main.getInstance().getLogger().info("Saved " + warp.getName() + "!");

            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    // Delete Warp.
    public boolean deleteWarp(String name) {
        var warpFolder = getWarpFolder();
        if(warpFolder == null) return false;

        var warpFile = new File(warpFolder, name.toLowerCase() + ".yml");
        if(!warpFile.exists()) return false;

        Main.getInstance().getLogger().info("Deleted " + name + "!");

        return warpFile.delete();
    }
}