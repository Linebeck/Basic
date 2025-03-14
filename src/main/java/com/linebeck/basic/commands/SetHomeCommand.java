package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.managers.HomeManager;
import com.linebeck.basic.objects.Home;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Set homes.
public class SetHomeCommand extends BaseCommand {

	private final HomeManager homeManager;

	private final Component setHomeText = TextHandler.setText(
			"You set your home!",
			Main.getInstance().getMainHexColor()
	);

	private Component setHomeByNameText(String name) {
		return TextHandler.setText(
				"You set a home named ",
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

	public SetHomeCommand(HomeManager homeManager) {
		super(
				"sethome",
				"basic.sethome",
				false
		);

		this.homeManager = homeManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length < 1) {
			homeManager.getHomeData().saveHome(player.getUniqueId(),  new Home("home", player.getLocation()));
			player.sendMessage(setHomeText);
			return;
		}

		String name = args[0];
		homeManager.getHomeData().saveHome(player.getUniqueId(),  new Home(name.toLowerCase(), player.getLocation()));
		player.sendMessage(setHomeByNameText(name));
	}
}