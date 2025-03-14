package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.managers.WarpManager;
import com.linebeck.basic.objects.Warp;
import com.linebeck.basic.utilities.LocationUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Set Warps.
public class SetWarpCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /setwarp <name>",
			Main.getInstance().getMainHexColor()
	);

	private final Component youMayNotSetText = TextHandler.setText(
			"You may not set a warp named 'list'.",
			Main.getInstance().getMainHexColor()
	);

	private Component setWarpText(String name) {
		return TextHandler.setText(
				"You set a warp named ",
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

	private Component unableToSetWarp(String name) {
		return TextHandler.setText(
				"Unable to set warp named ",
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

	private final WarpManager warpManager;

	public SetWarpCommand(WarpManager warpManager)	{
		super(
				"setwarp",
				"basic.setwarp",
				false
		);
		this.warpManager = warpManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length < 1) {
			player.sendMessage(syntaxText);
			return;
		}

		// Warp name.
		String name = args[0];

		if(name.equalsIgnoreCase("list")) {
			player.sendMessage(youMayNotSetText);
			return;
		}

		Location playerLocation = LocationUtil.getExactLocation(player.getLocation(), player.getEyeLocation().getDirection());

		var warp = new Warp(name.toLowerCase(), playerLocation);

		var status = warpManager.getWarpData().saveWarp(warp);

		if(status) {
			player.sendMessage(setWarpText(name));
		} else {
			player.sendMessage(unableToSetWarp(name));
		}
	}
}