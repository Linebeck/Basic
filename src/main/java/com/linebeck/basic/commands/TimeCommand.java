package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.utilities.NumericUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

// Time of the player.
public class TimeCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /time [time] or [day, night, noon, midnight]",
			Main.getInstance().getMainHexColor()
	);

	private Component theTimeIsText(long value) {
		return TextHandler.setText(
				"The time is ",
				Main.getInstance().getSubHexColor()
		).append(
				TextHandler.setText(
						String.valueOf(value),
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						"!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private final Component youHaveSetTimeText = TextHandler.setText(
			"You have set the time!",
			Main.getInstance().getMainHexColor()
	);

	public TimeCommand() {
		super(
				"time",
				"basic.time",
				false,
				new ArrayList<>(
						Arrays.asList(
								"day",
								"night",
								"noon",
								"midnight"
						)
				)
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length < 1) {
			var time = player.getWorld().getTime();
			player.sendMessage(theTimeIsText(time));
			return;
		}

		if(args.length > 1) {
			player.sendMessage(syntaxText);
			return;
		}

		String time = args[0];
		setTime(player, time);
	}

	private void setTime(Player player, String time) {
		switch(time) {
			case "day" -> player.getWorld().setTime(1000);
			case "noon", "afternoon" -> player.getWorld().setTime(6000);
			case "night" -> player.getWorld().setTime(13000);
			case "midnight" -> player.getWorld().setTime(18000);
			default -> {
				if(!NumericUtil.isNumeric(time)) {
					player.sendMessage(syntaxText);
					return;
				}
				long playerTime = Long.parseLong(time);
				player.getWorld().setTime(playerTime);
			}
		}
		player.sendMessage(youHaveSetTimeText);
	}
}