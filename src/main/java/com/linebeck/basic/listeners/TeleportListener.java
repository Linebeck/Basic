package com.linebeck.basic.listeners;

import com.linebeck.basic.managers.BasicPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    private final BasicPlayerManager basicPlayerManager;

    public TeleportListener(BasicPlayerManager basicPlayerManager) {
        this.basicPlayerManager = basicPlayerManager;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if(event.getCause().toString().equalsIgnoreCase("UNKNOWN")) return;

        var player = event.getPlayer();
        var previousLocation = event.getFrom();

        // Save Previous Location Data.
        var basicPlayerData = basicPlayerManager.getBasicPlayerData();
        if(basicPlayerData == null) return;

        var basicPlayer = basicPlayerData.getBasicPlayer(player.getUniqueId());

        if(basicPlayer == null) return;
        basicPlayerData.setPreviousLocation(player.getUniqueId(), previousLocation);
    }
}