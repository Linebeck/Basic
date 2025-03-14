package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.objects.TeleportRequest;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// TPA requests Accept.
public class TeleportRequestAcceptCommand extends BaseCommand {

	private Component acceptedText(String name) {
		return TextHandler.setText(
				"You have accepted ", Main.getInstance().getMainHexColor()
				).append(
						TextHandler.setText(
								name,
								Main.getInstance().getSubHexColor()
						)
				).append(
						TextHandler.setText(
								"'s teleport request!",
								Main.getInstance().getMainHexColor()
						)
		);
	}

	private Component hasAcceptedText(String name) {
		return TextHandler.setText(
				name,
				Main.getInstance().getSubHexColor()
		).append(
				TextHandler.setText(
						" has accepted your teleport request, please do not move!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	public TeleportRequestAcceptCommand() {
		super(
				"teleportrequestaccept",
				"basic.teleportrequestaccept",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		TeleportRequest tpRequest = TeleportRequest.getTeleportRequest(player.getUniqueId());

		if (tpRequest != null) {
			Player requestedPlayer = Bukkit.getPlayer(tpRequest.getRequestedPlayer());

			if (requestedPlayer != null) {

				// Player accepting.
				player.sendMessage(acceptedText(requestedPlayer.getName()));

				// Player requested.
				requestedPlayer.sendMessage(hasAcceptedText(requestedPlayer.getName()));
				
				tpRequest.teleport();
			}
		}
	}
}