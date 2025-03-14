package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.managers.WarpManager;
import com.linebeck.basic.objects.Warp;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteWarpCommand extends BaseCommand {

    private final Component syntaxText = TextHandler.setText(
            "Syntax: /delwarp [name]",
            Main.getInstance().getMainHexColor()
    );

    private final Component warpDoesNotExist = TextHandler.setText(
            "That warp does not exist!",
            Main.getInstance().getMainHexColor()
    );

    private Component successDeleteWarp(String name) {
        return TextHandler.setText(
                "You deleted the named ",
                Main.getInstance().getMainHexColor()
        ).append(
                TextHandler.setText(
                        name,
                        Main.getInstance().getSubHexColor()
                )
        ).append(
                TextHandler.setText(
                        "!",
                        Main.getInstance().getMainHexColor()
                )
        );
    }

    private Component unableToDeleteWarp(String name) {
        return TextHandler.setText(
                "Unable to delete warp named ",
                Main.getInstance().getMainHexColor()
        ).append(
                TextHandler.setText(
                        name,
                        Main.getInstance().getSubHexColor()
                )
        ).append(
                TextHandler.setText(
                        "!",
                        Main.getInstance().getMainHexColor()
                )
        );
    }

    private final WarpManager warpManager;

    public DeleteWarpCommand(WarpManager warpManager) {
        super(
          "deletewarp",
          "basic.deletewarp",
          false
        );
        this.warpManager = warpManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        var warpData = warpManager.getWarpData();
        if (warpData == null) return;

        if(args.length < 1) {
            player.sendMessage(syntaxText);
            return;
        }

        String name = args[0];

        var warp = warpData.getWarp(name);
        if(warp == null) {
            player.sendMessage(warpDoesNotExist);
            return;
        }

        var success = warpData.deleteWarp(name);

        if(success) {
            player.sendMessage(successDeleteWarp(name));
        } else {
            player.sendMessage(unableToDeleteWarp(name));
        }
    }

    @Override
    public List<String> getArguments() {
        return warpManager.getWarpData().getWarps().stream().map(Warp::getName).collect(Collectors.toList());
    }
}
