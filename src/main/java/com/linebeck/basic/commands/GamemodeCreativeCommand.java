package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCreativeCommand extends BaseCommand {

    private final Component gameModeCreative = TextHandler.setText(
            "You are now in creative mode!",
            Main.getInstance().getMainHexColor()
    );

    public GamemodeCreativeCommand() {
        super(
                "gmc",
                "basic.gamemode",
                false
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage(gameModeCreative);
    }
}