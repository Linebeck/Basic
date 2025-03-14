package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Feed yourself or others.
public class FeedCommand extends BaseCommand {

	private static final int DEFAULT_FOOD_LEVEL = 20;

	private final String othersPermission = "basic.feed.others";

	private final Component onlyPlayersText = TextHandler.setText(
			"Only players can use this command!",
			Main.getInstance().getSubHexColor()
	);

	private final Component feedOthersSyntaxText = TextHandler.setText(
			"Syntax: /feed [player name]",
			Main.getInstance().getMainHexColor()
	);

	private final Component noPermissionText = TextHandler.setText(
			"You don't have permission to feed others!",
			Main.getInstance().getMainHexColor()
	);

	private Component fedOtherText(String name) {
		return TextHandler.setText(
				"You have fed ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						name,
						Main.getInstance().getSubHexColor())
		).append(
				TextHandler.setText(
						"!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private final Component fedText = TextHandler.setText(
			"You have been fed!",
			Main.getInstance().getMainHexColor()
	);

	public FeedCommand() {
		super(
				"feed",
				"basic.feed",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(onlyPlayersText);
			return;
		}

		// Feed yourself.
		if (args.length == 0) {
			feedPlayer(player, player);
			return;
		}

		if (args.length > 1) {
			player.sendMessage(feedOthersSyntaxText);
			return;
		}

		// Check permission for others.
		if (!player.hasPermission(othersPermission)) {
			player.sendMessage(noPermissionText);
			return;
		}

		String targetName = args[0];
		Player targetPlayer = Bukkit.getPlayer(targetName);

		if (targetPlayer == null) {
			player.sendMessage(BasicTexts.playerIsNotOnlineText(targetName));
			return;
		}

		feedPlayer(player, targetPlayer);
	}

	// Set food & saturation level.
	private void feedPlayer(Player executor, Player target) {
		target.setFoodLevel(DEFAULT_FOOD_LEVEL);
		target.setSaturation(DEFAULT_FOOD_LEVEL);

		if (!executor.equals(target)) {
			executor.sendMessage(fedOtherText(target.getName()));
		}

		target.sendMessage(fedText);
	}
}