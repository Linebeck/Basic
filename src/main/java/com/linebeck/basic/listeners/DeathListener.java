package com.linebeck.basic.listeners;

import com.linebeck.basic.managers.BasicPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final BasicPlayerManager basicPlayerManager;

    public DeathListener(BasicPlayerManager basicPlayerManager) {
        this.basicPlayerManager = basicPlayerManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        var player = event.getEntity();

        var previousLocation = event.getEntity().getLocation();

        // Save Previous Location Data.
        var basicPlayerData = basicPlayerManager.getBasicPlayerData();
        if(basicPlayerData == null) return;

        var basicPlayer = basicPlayerData.getBasicPlayer(player.getUniqueId());

        if(basicPlayer == null) return;
        basicPlayerData.setPreviousLocation(player.getUniqueId(), previousLocation);
    }
}
