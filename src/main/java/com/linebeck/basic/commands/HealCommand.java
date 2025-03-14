package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Restore health.
public class HealCommand extends BaseCommand {

	private final String othersPermission = "basic.heal.others";

	private final Component healedText = TextHandler.setText(
			"You have been healed!",
			Main.getInstance().getMainHexColor()
	);

	private final Component noPermissionText = TextHandler.setText(
			"You don't have permission to heal others!",
			Main.getInstance().getMainHexColor()
	);

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /heal [player name]",
			Main.getInstance().getMainHexColor()
	);

	private Component othersHaveHealedText(String name) {
		return TextHandler.setText(
				"You have healed ",
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

	public HealCommand() {
		super(
				"heal",
				"basic.heal",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length < 1) {
			player.setHealth(player.getAttribute(Attribute.MAX_HEALTH).getValue());
			player.sendMessage(healedText);
			return;
		}

		if(!player.hasPermission(othersPermission)) {
			player.sendMessage(noPermissionText);
			return;
		}

		if(args.length > 1) {
			player.sendMessage(syntaxText);
			return;
		}

		// Get player.
		String playerName = args[0];
		Player otherPlayerHeal = Bukkit.getPlayer(playerName);

		if(otherPlayerHeal == null) {
			player.sendMessage(BasicTexts.playerIsNotOnlineText(playerName));
			return;
		}

		otherPlayerHeal.setHealth(otherPlayerHeal.getAttribute(Attribute.MAX_HEALTH).getValue());

		player.sendMessage(othersHaveHealedText(playerName));
		otherPlayerHeal.sendMessage(healedText);
	}
}