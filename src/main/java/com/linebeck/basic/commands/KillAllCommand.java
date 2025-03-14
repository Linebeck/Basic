package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Kill all players.
public class KillAllCommand extends BaseCommand {

	private final Component killedEveryoneText = TextHandler.setText(
			"You killed everyone!",
			Main.getInstance().getMainHexColor()
	);

	public KillAllCommand() {
		super(
				"killall",
				"basic.killall",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		var player = (Player) sender;
		
		for(var killPlayer : Bukkit.getOnlinePlayers()) {
			if(killPlayer.equals(player)) { continue; }
			killPlayer.setHealth(0);
		}

		player.sendMessage(killedEveryoneText);
	}
}