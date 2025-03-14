package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Check network ping.
public class PingCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /ping [player name]",
			Main.getInstance().getMainHexColor()
	);

	private Component selfPingText(int ping) {
		return TextHandler.setText(
				"Your ping is ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						String.valueOf(ping),
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						"!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private Component othersPingText(String name, int ping) {
		return TextHandler.setText(
				name,
				Main.getInstance().getSubHexColor()
		).append(
				TextHandler.setText(
						"'s ping is ",
						Main.getInstance().getMainHexColor()
				)
		).append(
				TextHandler.setText(
						String.valueOf(ping),
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						"!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	public PingCommand() {
		super(
				"ping",
				"basic.ping",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		// Get self ping.
		if(args.length < 1) {
			player.sendMessage(selfPingText(player.getPing()));
			return;
		}

		if(args.length > 1) {
			player.sendMessage(syntaxText);
			return;
		}

		// Get other player.
		String playerName = args[0];
		Player pingPlayer = Bukkit.getPlayer(playerName);

		if(pingPlayer == null) {
			player.sendMessage(BasicTexts.playerIsNotOnlineText(playerName));
			return;
		}

		player.sendMessage(othersPingText(playerName, pingPlayer.getPing()));
	}
}