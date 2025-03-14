package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.Calendar;

// Copyright & version information.
public class BasicCommand extends BaseCommand {

    @SuppressWarnings("UnstableApiUsage")
    private final Component headerText = TextHandler.setText(
            "-------[",
            "#808080"
    ).append(
            TextHandler.setText(
                    Main.getInstance().getPluginMeta().getName(),
                    "#FFD700",
                    "BOLD"
            )
    ).append(
            TextHandler.setText(
                    "]-------",
                    "#808080"
            )
    );

    @SuppressWarnings("UnstableApiUsage")
    private final Component versionText = TextHandler.setText(
            "Version: ",
            "#808080"
    ).append(
            TextHandler.setText(
                    Main.getInstance().getPluginMeta().getVersion(),
                    "#32CD32",
                    "BOLD"
            )
    );

    @SuppressWarnings("UnstableApiUsage")
    private final Component authorText = TextHandler.setText(
            "Created by: ",
            "#808080"
    ).append(
            TextHandler.setText(
                    Main.getInstance().getPluginMeta().getAuthors().toString(),
                    "#FFD700",
                    "BOLD"
            )
    );

    private final Component copyrightText = TextHandler.setText(
            "© ",
            "#808080",
            "BOLD"
    ).append(
            TextHandler.setText(
                    "2016 - " + Calendar.getInstance().get(Calendar.YEAR) + ".",
                    "#A9A9A9"
            )
    ).append(
            TextHandler.setText(
                    " All Rights Reserved.",
                    "#808080"
            )
    );

    public BasicCommand() {
        super(
                "basic",
                "basic.basic",
                true
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(headerText);
        sender.sendMessage(versionText);
        sender.sendMessage(authorText);
        sender.sendMessage(copyrightText);
    }
}