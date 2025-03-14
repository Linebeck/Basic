package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.managers.BasicPlayerManager;
import com.linebeck.basic.objects.BasicPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

// Teleport to last known location.
public class TeleportOfflineCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /teleportoffline [name or uuid] <player name>",
			Main.getInstance().getMainHexColor()
	);

	private final Component noLogoutText = TextHandler.setText(
			"Player does not have a logout location!",
			Main.getInstance().getMainHexColor()
	);

	private final Component doesNotExistText = TextHandler.setText(
			"That player does not exist!",
			Main.getInstance().getMainHexColor()
	);

	private Component teleportedToPlayerLastKnowLocationTest(String name) {
		return TextHandler.setText(
				"You have been teleported to ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						name,
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						"'s last known location!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private final BasicPlayerManager basicPlayerManager;

	public TeleportOfflineCommand(BasicPlayerManager basicPlayerManager) {
		super(
				"teleportoffline",
				"basic.teleportoffline",
				false,
				new ArrayList<>(
						Arrays.asList(
								"name",
								"uuid"
						)
				)
		);

		this.basicPlayerManager = basicPlayerManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length < 1) {
			player.sendMessage(syntaxText);
			return;
		}

		switch(args[0].toLowerCase()) {
			case "name" -> {
				String name = args[0];

				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(name);

				if(offlinePlayer == null) {
					player.sendMessage(doesNotExistText);
					return;
				}
				tpToLogoutLocation(player, offlinePlayer);
			}

			case "uuid" -> {
				String uuid = args[0];
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

				if(offlinePlayer == null) {
					player.sendMessage(doesNotExistText);
					return;
				}
				tpToLogoutLocation(player, offlinePlayer);
			}
		}
	}

	private void tpToLogoutLocation(Player player, OfflinePlayer offlinePlayer) {
		if(!basicPlayerManager.getBasicPlayerData().hasData(offlinePlayer.getUniqueId())) {
			player.sendMessage(noLogoutText);
			return;
		}

		// Get offline player.
		var uuid = offlinePlayer.getPlayer().getUniqueId();
		BasicPlayer basicPlayer = basicPlayerManager.getBasicPlayerData().getBasicPlayer(uuid);

		if(basicPlayer.getLogoutLocation() == null) {
			player.sendMessage(noLogoutText);
			return;
		}

		player.teleport(basicPlayer.getLogoutLocation());
		player.sendMessage(teleportedToPlayerLastKnowLocationTest(offlinePlayer.getName()));
	}
}