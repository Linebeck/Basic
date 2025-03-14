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

// Personal player's time.
public class PlayerTimeCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /playertime [time], [day, night, noon, midnight], or [reset]",
			Main.getInstance().getMainHexColor()
	);

	private final Component setTimeText = TextHandler.setText(
			"You have set your time!",
			Main.getInstance().getMainHexColor()
	);

	public PlayerTimeCommand() {
		super(
				"playertime",
				"basic.playertime",
				false,
				new ArrayList<>(
						Arrays.asList(
								"day",
								"night",
								"noon",
								"midnight",
								"reset"
						)
				)
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length < 1) {
			player.sendMessage(syntaxText);
			return;
		}
		String time = args[0];
		setTime(player, time);
	}

	private void setTime(Player player, String time) {
		switch(time) {
			case "noon", "afternoon", "day" -> player.setPlayerTime(6000, true);
			case "night" -> player.setPlayerTime(13000, true);
			case "midnight" -> player.setPlayerTime(18000, true);
			case "reset" -> player.resetPlayerTime();
			default -> {
				if(!NumericUtil.isNumeric(time)) {
					player.sendMessage(syntaxText);
					return;
				}
				long playerTime = Long.parseLong(time);
				player.setPlayerTime(playerTime, true);
			}
		}
		player.sendMessage(setTimeText);
	}
}