package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

// Gamemode.
public class GamemodeCommand extends BaseCommand {

	private final String othersPermission = "basic.gamemode.others";

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /gm [survival, creative, adventure, or spectator]",
			Main.getInstance().getMainHexColor()
	);

	private final Component noPermissionText = TextHandler.setText(
			"You don't have permission to set other player's gamemode!",
			Main.getInstance().getMainHexColor()
	);

	private Component setGamemodeText(String name) {
		return TextHandler.setText(
				"You set your gamemode to ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						name.toLowerCase(),
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						"!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private Component otherSetGamemodeAdminText(String playerName, String gamemodeName) {
		return TextHandler.setText(
				"You have set ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						playerName,
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						"'s gamemode to ",
						Main.getInstance().getMainHexColor()
				)
		).append(
				TextHandler.setText(
						gamemodeName.toLowerCase(),
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						"!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private Component otherSetGamemodeText(String name) {
		return TextHandler.setText(
				"Your gamemode got set to ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						name.toLowerCase(),
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						"!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	public GamemodeCommand() {
		super(
				"gamemode",
				"basic.gamemode",
				false,
				new ArrayList<>(
						Arrays.asList(
								"survival",
								"creative",
								"adventure",
								"spectator",
								"s",
								"c",
								"a",
								"sp",
								"0",
								"1",
								"2",
								"3")
				)
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length == 0) {
			player.sendMessage(syntaxText);
			return;
		}

		if(args.length < 2) {

			GameMode gamemode = getGameMode(args[0]);

			if(gamemode == null) {
				player.sendMessage(syntaxText);
				return;
			}

			player.setGameMode(gamemode);
			player.sendMessage(setGamemodeText(gamemode.name()));
			return;
		}

		if(!player.hasPermission(othersPermission)) {
			player.sendMessage(noPermissionText);
			return;
		}

		if(args.length != 2) {
			player.sendMessage(syntaxText);
			return;
		}

		// Get other player.
		String playerName = args[1];
		Player otherPlayerGm = Bukkit.getPlayer(playerName);

		if(otherPlayerGm == null) {
			player.sendMessage(BasicTexts.playerIsNotOnlineText(playerName));
			return;
		}

		GameMode gameMode = getGameMode(args[0]);

		if(gameMode == null) {
			player.sendMessage(syntaxText);
			return;
		}

		otherPlayerGm.setGameMode(gameMode);

		player.sendMessage(otherSetGamemodeAdminText(playerName, gameMode.name()));

		otherPlayerGm.sendMessage(otherSetGamemodeText(gameMode.name()));

	}

	private GameMode getGameMode(String gameMode) {
		switch (gameMode) {
			case "survival", "s", "0" -> {
				return GameMode.SURVIVAL;
			}
			case "creative", "c", "1" -> {
				return GameMode.CREATIVE;
			}
			case "adventure", "a", "2" -> {
				return GameMode.ADVENTURE;
			}
			case "spectator", "sp", "3" -> {
				return GameMode.SPECTATOR;
			}
		}
		return null;
	}
}