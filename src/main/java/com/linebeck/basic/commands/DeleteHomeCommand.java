package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.interfaces.PersonalCommand;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.managers.HomeManager;
import com.linebeck.basic.objects.Home;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

// Home Deletion.
public class DeleteHomeCommand extends BaseCommand implements PersonalCommand {

	private final Component successRemoveText = TextHandler.setText(
			"You removed your home!",
			Main.getInstance().getMainHexColor()
	);

	private Component doesNotExistByNameText(String name) {
		return TextHandler.setText(
				"The home named ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						name,
						Main.getInstance().getSubHexColor())
		).append(
				TextHandler.setText(
						" does not exist!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private Component successRemoveByNameText(String name) {
		return TextHandler.setText(
				"You removed a home named ",
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

	private Component failedRemoveByNameText(String name) {
		return TextHandler.setText(
				"Failed to remove ",
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

	private final HomeManager homeManager;

	public DeleteHomeCommand(HomeManager homeManager) {
		super(
				"deletehome",
				"basic.deletehome",
				false
		);
		this.homeManager = homeManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		var homeData = homeManager.getHomeData();
		if(homeData == null) return;

		if(args.length < 1) {
			homeData.deleteHome(player.getUniqueId(), "home");
			player.sendMessage(successRemoveText);
			return;
		}

		String name = args[0];

		// Verify home exists.
		var home = homeData.getHome(player.getUniqueId(), name);
		if(home == null) {
			player.sendMessage(doesNotExistByNameText(name));
			return;
		}

		// Remove home from file.
		var success = homeData.deleteHome(player.getUniqueId(), name);

		if(success) {
			player.sendMessage(successRemoveByNameText(name));
		} else {
			player.sendMessage(failedRemoveByNameText(name));
		}
	}

	@Override
	public void setPersonalArguments(Player player) {
		var homeData = homeManager.getHomeData();
		var homes = homeData.getHomes(player.getUniqueId()).stream().map(Home::getName).collect(Collectors.toList());
		setArguments(homes);
	}
}