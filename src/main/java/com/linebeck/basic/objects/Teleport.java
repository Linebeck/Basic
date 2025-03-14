package com.linebeck.basic.objects;

import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Teleport {

    private BukkitTask teleportationTask;

    private final Player player;

    private Location location;

    private Entity entity;

    private int lapse;

    public Teleport(Player player, Location location, int lapse) {
        this.player = player;
        this.location = location;
        this.lapse = lapse;
        teleport();
    }

    public Teleport(Player player, Entity entity, int lapse) {
        this.player = player;
        this.entity = entity;
        this.lapse = lapse;
        teleport();
    }

    private void teleport() {
        Location currentLocation = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        teleportationTask = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            if(lapse == 0 || player.hasPermission("Basic.Teleport.Bypass")) {
                if(entity != null) { location = entity.getLocation(); }
                player.teleport(location);
                teleportationTask.cancel();
                return;
            }
            else {
                if(currentLocation.getX() != player.getLocation().getX() || currentLocation.getY() != player.getLocation().getY() || currentLocation.getZ() != player.getLocation().getZ()) {
                    player.sendMessage(TextHandler.setText("Teleportation cancelled due to movement!", Main.getInstance().getMainHexColor()));
                    teleportationTask.cancel();
                    return;
                }

                player.showTitle(Title.title(TextHandler.setText("Teleporting in ", Main.getInstance().getMainHexColor()),
                        TextHandler.setText(String.valueOf(lapse), Main.getInstance().getSubHexColor(), "BOLD")));
            }
            lapse--;
        }, 0L, 20L);
    }
}
