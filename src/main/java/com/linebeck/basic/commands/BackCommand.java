package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.managers.BasicPlayerManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Go back to the previous location.
public class BackCommand extends BaseCommand {

    private final Component successBackText = TextHandler.setText(
            "Whoosh!",
            Main.getInstance().getMainHexColor()
    );

    private final Component cantGoBackText = TextHandler.setText(
            "You do not have a previous location to go back to!",
            Main.getInstance().getMainHexColor()
    );

    private final BasicPlayerManager basicPlayerManager;

    public BackCommand(BasicPlayerManager basicPlayerManager) {
        super(
                "back",
                "basic.back",
                false
        );
        this.basicPlayerManager = basicPlayerManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        var player = (Player) sender;

        var basicPlayer = basicPlayerManager
                .getBasicPlayerData()
                .getBasicPlayer(player.getUniqueId());

        if(basicPlayer == null) return;
        if(basicPlayer.getPreviousLocation() != null) {
            player.teleport(basicPlayer.getPreviousLocation());
            player.sendMessage(successBackText);
        }
        else {
            player.sendMessage(cantGoBackText);
        }
    }
}