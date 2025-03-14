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

// Home command.
public class HomeCommand extends BaseCommand implements PersonalCommand {

	private final Component noHomesText = TextHandler.setText(
			"You do not have any homes!",
			Main.getInstance().getMainHexColor()
	) ;

	private final HomeManager homeManager;

	public HomeCommand(HomeManager homeManager) {
		super(
				"home",
				"basic.home",
				false
		);

		this.homeManager = homeManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args){
		Player player = (Player) sender;

		if(homeManager.getHomeData().getHomes(player.getUniqueId()).isEmpty()) {
			player.sendMessage(noHomesText);
			return;
		}

		if(args.length >= 1) {
			String name = args[0];
			Home home = homeManager.getHomeData().getHome(player.getUniqueId(), name.toLowerCase());
			if(home != null) {
				player.teleport(home.getLocation());
			}
			return;
		}

		Home home = homeManager.getHomeData().getHome(player.getUniqueId(), "home");

		if(home != null) {
			player.teleport(home.getLocation());
		}
	}

	@Override
	public void setPersonalArguments(Player player) {
		var homeData = homeManager.getHomeData();
		var homes = homeData.getHomes(player.getUniqueId()).stream().map(Home::getName).collect(Collectors.toList());
		setArguments(homes);
	}
}
