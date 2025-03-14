package com.linebeck.basic.listeners;

import com.linebeck.basic.internal.Main;
import com.linebeck.basic.managers.BasicPlayerManager;
import com.linebeck.basic.objects.BasicPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final BasicPlayerManager basicPlayerManager;

    public JoinListener(BasicPlayerManager basicPlayerManager) {
        this.basicPlayerManager = basicPlayerManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        var showMessage = Main.getInstance().getShowJoinMessage();
        if(!showMessage) {
            event.joinMessage(null);
        }
        var player = event.getPlayer();
        registerPlayer(player);
    }

    // Register Player Data.
    private void registerPlayer(Player player) {
        var basicPlayerData = basicPlayerManager.getBasicPlayerData();
        if(basicPlayerData == null) return;

        var uuid = player.getUniqueId();

        // Create or Update Data Folder.
        if(basicPlayerData.hasData(uuid)) {
            var existingBasicPlayer = basicPlayerData.getBasicPlayer(uuid);
            var updatedBasicPlayer = new BasicPlayer(
                    uuid,
                    player.getName(),
                    player.getAddress().toString(),
                    existingBasicPlayer.getPreviousLocation(),
                    existingBasicPlayer.getLogoutLocation()
            );
            basicPlayerData.saveBasicPlayer(updatedBasicPlayer);
        } else {
            var newBasicPlayer = new BasicPlayer(
                    player.getUniqueId(),
                    player.getName(),
                    player.getAddress().toString(),
                    null,
                    null
            );
            basicPlayerData.createPlayerFolder(newBasicPlayer);
        }
    }
}