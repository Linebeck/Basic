package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.objects.TeleportRequest;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Teleport Request Deny
public class TeleportRequestDenyCommand extends BaseCommand {

	private final Component noRequestsText = TextHandler.setText(
			"You have no teleport requests!",
			Main.getInstance().getMainHexColor()
	);

	private final Component playerMustBeOfflineNowText = TextHandler.setText(
			"Player is no longer online for requests!",
			Main.getInstance().getMainHexColor()
	);

	private Component declinedRequestByNameText(String name) {
		return TextHandler.setText(
				"You have declined ",
				Main.getInstance().getMainHexColor()
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

	private Component playerDeniedYourRequest(String name) {
		return TextHandler.setText(
				name,
				Main.getInstance().getSubHexColor()
		).append(
				TextHandler.setText(
						" has declined your teleport request!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	public TeleportRequestDenyCommand() {
		super(
				"teleportrequestdeny",
				"basic.teleportdeny",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		TeleportRequest tpRequest = TeleportRequest.getTeleportRequest(player.getUniqueId());

		if (tpRequest == null) {
			player.sendMessage(noRequestsText);
			return;
		}

		Player requestedPlayer = Bukkit.getPlayer(tpRequest.getRequestedPlayer());
		if (requestedPlayer == null) {
			player.sendMessage(playerMustBeOfflineNowText);
			TeleportRequest.teleportRequests.remove(tpRequest);
			return;
		}

		player.sendMessage(declinedRequestByNameText(requestedPlayer.getName()));

		requestedPlayer.sendMessage(playerDeniedYourRequest(player.getName()));

		TeleportRequest.teleportRequests.remove(tpRequest);
	}
}
