package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.managers.HomeManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// List all available homes.
public class ListHomesCommand extends BaseCommand {

	private final HomeManager homeManager;

	public ListHomesCommand(HomeManager homeManager) {
		super(
				"listhomes",
				"basic.listhomes",
				false
		);

		this.homeManager = homeManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		var player = (Player) sender;
		homeManager.showList(player);
	}
}