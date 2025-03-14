package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.managers.BasicPlayerManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

// Player Information.
public class WhoIsCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /whois [name or uuid] <playername>",
			Main.getInstance().getMainHexColor()
	);

	private final Component neverPlayedBefore = TextHandler.setText(
			"That player has never played before!",
			Main.getInstance().getMainHexColor()
	);

	private Component doesNotHaveAFileByNameText(String name) {
		return TextHandler.setText(
				name,
				Main.getInstance().getSubHexColor()
		).append(
				TextHandler.setText(
						" does not have a file!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private final BasicPlayerManager basicPlayerManager;

	public WhoIsCommand(BasicPlayerManager basicPlayerManager) {
		super(
				"whois",
				"basic.whois",
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
		var player = (Player) sender;

		if(args.length < 2) {
			player.sendMessage(syntaxText);
			return;
		}

		switch(args[0].toLowerCase()) {
			case "name" -> {
				String name = args[1];
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);

				if(offlinePlayer == null) {
					player.sendMessage(neverPlayedBefore);
					return;
				}

				getWhois(player, offlinePlayer);
			}

			case "uuid" -> {
				String uuid = args[1];
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

				if(offlinePlayer == null) {
					player.sendMessage(BasicTexts.playerDoesNotExist);
					return;
				}

				getWhois(player, offlinePlayer);
			}
		}
	}

	private void getWhois(Player commandPlayer, OfflinePlayer offlinePlayer) {
		var basicPlayerData = basicPlayerManager.getBasicPlayerData();

		if(!basicPlayerData.hasData(offlinePlayer.getUniqueId())) {
			commandPlayer.sendMessage(doesNotHaveAFileByNameText(offlinePlayer.getName()));
			return;
		}

		var basicPlayer = basicPlayerData.getBasicPlayer(offlinePlayer.getUniqueId());

		if(basicPlayer == null) {
			commandPlayer.sendMessage(doesNotHaveAFileByNameText(offlinePlayer.getName()));
			return;
		}

		Date date = new Date(offlinePlayer.getLastSeen());
		String dateAsString = getDateFormatted(date);

		// Player Information.
		commandPlayer.sendMessage(TextHandler.setText("----------------------------", Main.getInstance().getMainHexColor()));
		commandPlayer.sendMessage(TextHandler.setText(offlinePlayer.getName(), Main.getInstance().getSubHexColor()));
		commandPlayer.sendMessage(TextHandler.setText(basicPlayer.getUUID().toString(), Main.getInstance().getSubHexColor()));
		commandPlayer.sendMessage(TextHandler.setText(basicPlayer.getIPAddress(), Main.getInstance().getSubHexColor()));
		commandPlayer.sendMessage(TextHandler.setText(dateAsString, Main.getInstance().getSubHexColor()));
		commandPlayer.sendMessage(TextHandler.setText("----------------------------", Main.getInstance().getMainHexColor()));
	}

	private String getDateFormatted(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss a z");
		return dateFormat.format(date);
	}
}