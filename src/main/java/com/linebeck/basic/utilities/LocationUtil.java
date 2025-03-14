package com.linebeck.basic.utilities;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class LocationUtil {

	public static Location getExactLocation(Location location, float yaw, float pitch) {
		Location exactLocation = location.clone();
		exactLocation.setYaw(yaw); exactLocation.setPitch(pitch);
		return exactLocation;
	}

	public static Location getExactLocation(Location location, Vector direction) {
		Location exactLocation = location.clone();
		exactLocation.setDirection(direction);
		return exactLocation;
	}

	public static Location getExactLocation(World world, double x, double y, double z, float yaw, float pitch) {
		Location exactLocation = new Location(world, x, y, z);
		exactLocation.setYaw(yaw); exactLocation.setPitch(pitch);
		return exactLocation;
	}

	public static Location getExactLocation(World world, double x, double y, double z, Vector direction) {
		Location exactLocation = new Location(world, x, y, z);
		exactLocation.setDirection(direction);
		return exactLocation;
	}

	public static String getDirection(Entity entity) {
		double rotation = (entity.getLocation().getYaw() - 90) % 360;

		if(rotation < 0) { rotation += 360.0; }

		if(0 <= rotation && rotation < 22.5) {
			return "W";
		} else if (22.5 <= rotation && rotation < 67.5) {
			return "NW";
		} else if (67.5 <= rotation && rotation < 112.5) {
			return "N";
		} else if (112.5 <= rotation && rotation < 157.5) {
			return "NE";
		} else if (157.5 <= rotation && rotation < 202.5) {
			return "E";
		} else if (202.5 <= rotation && rotation < 247.5) {
			return "SE";
		} else if (247.5 <= rotation && rotation < 292.5) {
			return "S";
		} else if (292.5 <= rotation && rotation < 337.5) {
			return "SW";
		} else if (337.5 <= rotation && rotation < 360.0) {
			return "W";
		} else {
			return "";
		}
	}

	public static BlockFace getFacingDirection(Entity entity) {
		return switch (getDirection(entity)) {
			case "N" -> BlockFace.NORTH;
			case "NE" -> BlockFace.NORTH_EAST;
			case "NW" -> BlockFace.NORTH_WEST;
			case "E" -> BlockFace.EAST;
			case "S" -> BlockFace.SOUTH;
			case "SE" -> BlockFace.SOUTH_EAST;
			case "SW" -> BlockFace.SOUTH_WEST;
			case "W" -> BlockFace.WEST;
			default -> null;
		};
	}

	public static BlockFace getMirrorFacingDirection(Entity entity) {
		return switch (getDirection(entity)) {
			case "N" -> BlockFace.SOUTH;
			case "NE" -> BlockFace.SOUTH_WEST;
			case "NW" -> BlockFace.SOUTH_EAST;
			case "E" -> BlockFace.WEST;
			case "S" -> BlockFace.NORTH;
			case "SE" -> BlockFace.NORTH_WEST;
			case "SW" -> BlockFace.NORTH_EAST;
			case "W" -> BlockFace.EAST;
			default -> null;
		};
	}

	public static Map<String, Object> serializeLocation(Location location, boolean isPlayerLocation) {
		if(location == null) return null;
		Map<String, Object> locationMap = new HashMap<>();
		locationMap.put("World", location.getWorld().getName());
		locationMap.put("X", location.getX());
		locationMap.put("Y", location.getY());
		locationMap.put("Z", location.getZ());

		if(isPlayerLocation) {
			locationMap.put("Yaw", location.getYaw());
			locationMap.put("Pitch", location.getPitch());
		}

		return locationMap;
	}
}