package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Flight.
public class FlyCommand extends BaseCommand {

	private final String othersPermission = "basic.fly.others";

	private final Component notFlyingText = TextHandler.setText(
			"You are no longer flying!",
			Main.getInstance().getMainHexColor()
	);

	private final Component flyingText = TextHandler.setText(
			"You are now flying!",
			Main.getInstance().getMainHexColor()
	);

	private final Component noOtherPermissionText = TextHandler.setText(
			"You don't have permission to allow others to fly!",
			Main.getInstance().getMainHexColor()
	);

	private final Component flyOthersSyntaxText = TextHandler.setText(
			"Syntax: /fly [player name]",
			Main.getInstance().getMainHexColor()
	);

	// Flight disabled texts for admin & others.
	private Component otherFlightDisabledAdminText(String name) {
		return TextHandler.setText(
				"You have disabled fly for ",
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

	private final Component otherFlightDisabledText = TextHandler.setText(
			"Flight disabled!",
			Main.getInstance().getMainHexColor()
	);

	// Flight enabled texts for admin & others.
	private Component otherFlightEnabledAdminText(String name) {
		return TextHandler.setText(
				"You have enabled fly for ",
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

	private final Component otherFlightEnabledText = TextHandler.setText(
			"Flight enabled!",
			Main.getInstance().getMainHexColor()
	);

	public FlyCommand() {
		super(
				"fly",
				"basic.fly",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		// Toggle fly.
		if(args.length < 1) {
			if (player.getAllowFlight()) {
				player.setAllowFlight(false);
				player.sendMessage(notFlyingText);
			}
			else if (!player.getAllowFlight()) {
				player.setAllowFlight(true);
				player.sendMessage(flyingText);
			}
			return;
		}

		if(!player.hasPermission(othersPermission)) {
			player.sendMessage(noOtherPermissionText);
			return;
		}

		if(args.length > 1) {
			player.sendMessage(flyOthersSyntaxText);
			return;
		}

		// Get other player.
		String playerName = args[0];
		Player otherPlayerSetFly = Bukkit.getPlayer(playerName);

		if(otherPlayerSetFly == null) {
			player.sendMessage(BasicTexts.playerIsNotOnlineText(playerName));
			return;
		}

		// Toggle flight for other player.
		if (otherPlayerSetFly.getAllowFlight()) {
			otherPlayerSetFly.setAllowFlight(false);

			player.sendMessage(otherFlightDisabledAdminText(playerName));
			otherPlayerSetFly.sendMessage(otherFlightDisabledText);
		}
		else if (!otherPlayerSetFly.getAllowFlight()) {
			otherPlayerSetFly.setAllowFlight(true);

			player.sendMessage(otherFlightEnabledAdminText(playerName));
			otherPlayerSetFly.sendMessage(otherFlightEnabledText);
		}
	}
}