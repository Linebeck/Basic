package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.managers.WarpManager;
import com.linebeck.basic.objects.Teleport;
import com.linebeck.basic.objects.Warp;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class WarpCommand extends BaseCommand {

	private Component noWarpByName(String name) {
		return TextHandler.setText(
				"There is no warp named ",
				Main.getInstance().getMainHexColor()
				).append(
						TextHandler.setText(
								name,
								Main.getInstance().getSubHexColor()))
				.append(
						TextHandler.setText(
								"!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private final WarpManager warpManager;

	public WarpCommand(WarpManager warpManager) {
		super(
				"warp",
				"basic.warp",
				false
		);

		this.warpManager = warpManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		var player = (Player) sender;

		if(args.length < 1) {
			warpManager.showList(player);
			return;
		}

		if(args[0].equalsIgnoreCase("list")) {
			warpManager.showList(player);
			return;
		}

		String name = args[0];
		Warp warp = warpManager.getWarpData().getWarp(name.toLowerCase());

		if(warp == null) {
			player.sendMessage(noWarpByName(name));
			return;
		}

		new Teleport(player, warp.getLocation(), Main.getInstance().getWarpCoolDown());
	}

	@Override
	public List<String> getArguments() {
		return warpManager.getWarpData().getWarps().stream().map(Warp::getName).collect(Collectors.toList());
	}
}