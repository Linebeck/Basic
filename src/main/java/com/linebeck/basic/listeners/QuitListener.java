package com.linebeck.basic.listeners;

import com.linebeck.basic.internal.Main;
import com.linebeck.basic.managers.BasicPlayerManager;
import com.linebeck.basic.objects.BasicPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final BasicPlayerManager basicPlayerManager;

    public QuitListener(BasicPlayerManager basicPlayerManager) {
        this.basicPlayerManager = basicPlayerManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        var showMessage = Main.getInstance().getShowLeaveMessage();
        if(!showMessage) {
            event.quitMessage(null);
        }

        var player = event.getPlayer();

        savePlayer(player);
    }

    // Save Player's Data.
    private void savePlayer(Player player) {
        var basicPlayerData = basicPlayerManager.getBasicPlayerData();
        if(basicPlayerData == null) return;

        // Get Basic Player Data.
        var uuid = player.getUniqueId();
        var existingBasicPlayer = basicPlayerData.getBasicPlayer(uuid);
        if(existingBasicPlayer == null) return;

        var updatedBasicPlayer = new BasicPlayer(
                uuid,
                player.getName(),
                player.getAddress().toString(),
                existingBasicPlayer.getPreviousLocation(),
                player.getLocation()
        );

        basicPlayerData.saveBasicPlayer(updatedBasicPlayer);
    }
}