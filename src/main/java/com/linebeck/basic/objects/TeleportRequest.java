package com.linebeck.basic.objects;

import com.linebeck.basic.internal.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class TeleportRequest {
    public static HashSet<TeleportRequest> teleportRequests = new HashSet<>();

    private final UUID requestedPlayer;
    private final UUID answerPlayer;

    public UUID getRequestedPlayer() { return requestedPlayer; }

    public UUID getAnswerPlayer() { return answerPlayer; }

    public TeleportRequest(UUID requestingPlayer, UUID answeringPlayer) {
        this.requestedPlayer = requestingPlayer;
        this.answerPlayer = answeringPlayer;

        teleportRequests.add(this);
    }

    public void teleport() {
        Player player = Bukkit.getPlayer(answerPlayer);
        Player teleportRequestedPlayer = Bukkit.getPlayer(requestedPlayer);

        new Teleport(teleportRequestedPlayer, player, Main.getInstance().getTeleportCoolDown());

        teleportRequests.remove(this);
    }

    public static TeleportRequest getTeleportRequest(UUID uuid) {
        for (TeleportRequest teleportRequest : teleportRequests) {
            if (teleportRequest.getAnswerPlayer().equals(uuid)) {
                return teleportRequest;
            }
        }
        return null;
    }
}