package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// When player was last seen.
public class SeenCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /seen <playername>",
			Main.getInstance().getMainHexColor()
	);

	private Component playerNeverPlayedText = TextHandler.setText(
			"That player has never played before!",
			Main.getInstance().getMainHexColor()
	);

	private Component lastSeenText(String dateAsString) {
		return TextHandler.setText(
				"Last Seen: ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						dateAsString,
						Main.getInstance().getSubHexColor()
				)
		);
	}

	public SeenCommand() {
		super(
				"seen",
				"basic.seen",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length < 1) {
			player.sendMessage(syntaxText);
			return;
		}

		// Get player.
		String name = args[0];
		Player onlinePlayer = Bukkit.getPlayer(name);

		if(onlinePlayer == null) {
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(name);

			if(offlinePlayer == null) {
				player.sendMessage(playerNeverPlayedText);
				return;
			}

			Date date = new Date(offlinePlayer.getLastSeen());
			String dateAsString = getDateFormatted(date);

			player.sendMessage(lastSeenText(dateAsString));
			return;
		}

		Date date = new Date(onlinePlayer.getLastSeen());
		String dateAsString = getDateFormatted(date);

		player.sendMessage(lastSeenText(dateAsString));
	}

	private String getDateFormatted(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss a z");
		return dateFormat.format(date);
	}
}