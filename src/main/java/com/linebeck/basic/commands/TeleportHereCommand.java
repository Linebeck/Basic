package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Teleport player to you.
public class TeleportHereCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /teleporthere [player name]",
			Main.getInstance().getMainHexColor()
	);

	private Component youTeleportedPlayerText(String name) {
		return TextHandler.setText(
				"You have teleported ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						name,
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						" to you!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	public TeleportHereCommand() {
		super(
				"teleporthere",
				"basic.teleporthere",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length != 1) {
			player.sendMessage(syntaxText);
			return;
		}

		// Get player.
		String playerName = args[0];
		Player otherPlayerTP = Bukkit.getPlayer(playerName);

		if(otherPlayerTP == null) {
			player.sendMessage(BasicTexts.playerIsNotOnlineText(playerName));
			return;
		}

		otherPlayerTP.teleport(player.getLocation());

		player.sendMessage(youTeleportedPlayerText(otherPlayerTP.getName()));
	}
}
