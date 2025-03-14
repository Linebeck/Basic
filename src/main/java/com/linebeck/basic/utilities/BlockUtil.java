package com.linebeck.basic.utilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BlockUtil {

    public static ArrayList<Block> getNearbyBlocks(Block start, int radius) {
        ArrayList<Block> blocks = new ArrayList<>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++) {
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++) {
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++) {
                    Location location = new Location(start.getWorld(), x, y, z);
                    blocks.add(location.getBlock());
                }
            }
        }

        return blocks;
    }

    public static ArrayList<Block> getNearbyBlocksByType(Block start, int radius, Material material) {
        ArrayList<Block> blocks = new ArrayList<>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++) {
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++) {
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++) {
                    Location location = new Location(start.getWorld(), x, y, z);
                    if(location.getBlock().getType() != material) { continue; }
                    blocks.add(location.getBlock());
                }
            }
        }

        return blocks;
    }

    public static Block getDirectionBlock(Player player) {
        BlockFace blockFace = LocationUtil.getFacingDirection(player);
        return player.getLocation().getBlock().getRelative(blockFace);
    }
}
