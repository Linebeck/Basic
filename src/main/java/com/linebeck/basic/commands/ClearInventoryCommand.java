package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Clear inventory.
public class ClearInventoryCommand extends BaseCommand {

    private final String othersPermission = "basic.clearinventory.others";

    private final Component successClearText = TextHandler.setText(
            "You cleared your inventory!",
            Main.getInstance().getMainHexColor()
    );

    private final Component syntaxText = TextHandler.setText(
            "Syntax: /clearinventory <Optional: [player name]>",
            Main.getInstance().getMainHexColor()
    );

    private Component failedPlayerClearText() {
        return TextHandler.setText(
                "You do not have permission to clear another player's inventory!",
                Main.getInstance().getMainHexColor()
        );
    }

    private Component successPlayerClearText(String name) {
        return TextHandler.setText(
                "You have cleared ",
                Main.getInstance().getMainHexColor()
        ).append(
                TextHandler.setText(
                        name,
                        Main.getInstance().getSubHexColor()
                ).append(
                        TextHandler.setText(
                                "'s inventory!",
                                Main.getInstance().getMainHexColor()
                        )
                )
        );
    }

    public ClearInventoryCommand() {
        super(
                "clearinventory",
                "basic.clearinventory",
                false
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        var player = (Player) sender;

        if (args.length < 1) {
            player.getInventory().clear();
            player.sendMessage(successClearText);
            return;
        }

        if (!player.hasPermission(othersPermission)) {
            player.sendMessage(failedPlayerClearText());
            return;
        }

        if (args.length > 1) {
            player.sendMessage(syntaxText);
            return;
        }

        // Get Other Player.
        var playerName = args[0];
        Player otherPlayer = Bukkit.getPlayer(playerName);

        if (otherPlayer == null) {
            player.sendMessage(BasicTexts.playerIsNotOnlineText(playerName));
            return;
        }

        // Clear Other Player's Inventory.
        otherPlayer.getInventory().clear();
        player.sendMessage(successPlayerClearText(args[0]));
    }
}