package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class WeatherCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /weather <clear or storm>",
			Main.getInstance().getMainHexColor()
	);

	private Component youSetTheWeather(String weather) {
		return TextHandler.setText(
				"You set the weather to ",
				Main.getInstance().getMainHexColor()
				).append(
						TextHandler.setText(
								weather,
								Main.getInstance().getSubHexColor()
				).append(
						TextHandler.setText(
								"!",
								Main.getInstance().getMainHexColor()
						)
				)
		);
	}

	public WeatherCommand() {
		super(
				"weather",
				"basic.weather",
				false,
				new ArrayList<>(
						Arrays.asList(
								"sun",
								"clear",
								"rain",
								"storm"
						)
				)
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		var player = (Player) sender;

		var weather = args[0];

		switch(weather.toLowerCase()) {
			case "sun", "sunny", "clear" -> player.getWorld().setStorm(false);
			case "rain", "storm" -> player.getWorld().setStorm(true);
			default -> {
				player.sendMessage(syntaxText);
				return;
			}
		}

		player.sendMessage(youSetTheWeather(weather));
	}
}