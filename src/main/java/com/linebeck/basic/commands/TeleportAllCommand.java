package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Teleport everyone to you.
public class TeleportAllCommand extends BaseCommand {

	private Component teleportedToPlayerText(String name) {
		return TextHandler.setText(
				"You have been teleported to ",
						Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						name,
						Main.getInstance().getSubHexColor()
				)
		);
	}

	private final Component teleportAllText = TextHandler.setText(
			"You teleported everyone to you!",
			Main.getInstance().getMainHexColor()
	);

	public TeleportAllCommand() {
		super(
				"teleportall",
				"basic.teleportall",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {	
		Player player = (Player) sender;
		
		for(Player teleportPlayer : Bukkit.getOnlinePlayers()) {
			teleportPlayer.teleport(player);
			teleportPlayer.sendMessage(teleportedToPlayerText(player.getName()));
		}
		
		player.sendMessage(teleportAllText);
	}
}
