package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.objects.TeleportRequest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// /Tpr requests (Teleport Request).
public class TeleportRequestCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /tpr [player name]",
			Main.getInstance().getMainHexColor()
	);

	private final Component youCantTeleportToSelfText = TextHandler.setText(
			"You cannot teleport request yourself!",
			Main.getInstance().getMainHexColor()
	);

	private Component youAlreadySentATeleportText(String name) {
		return TextHandler.setText(
				"You already sent ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						name,
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						" a teleport request!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private Component playerSentTeleportRequest(String name) {
		return TextHandler.setText(
				name,
				Main.getInstance().getSubHexColor()
		).append(
				TextHandler.setText(
						" sent you a teleport request!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private Component teleportRequestSent(String name) {
		return TextHandler.setText(
				"You have sent a teleport request to ",
				Main.getInstance().getMainHexColor()
				).append(
						TextHandler.setText(
								name,
								Main.getInstance().getSubHexColor())
				).append(
						TextHandler.setText(
								"!",
								Main.getInstance().getMainHexColor()
						)
		);
	}

	public TeleportRequestCommand() {
		super(
				"teleportrequest",
				"basic.teleportrequest",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length != 1) {
			player.sendMessage(syntaxText);
			return;
		}

		String playerName = args[0];
		Player otherPlayerTpr = Bukkit.getPlayer(playerName);

		if(otherPlayerTpr == null) {
			player.sendMessage(BasicTexts.playerIsNotOnlineText(playerName));
		}

		if(player == otherPlayerTpr) {
			player.sendMessage(youCantTeleportToSelfText);
			return;
		}

		var uuid = otherPlayerTpr.getUniqueId();
		TeleportRequest tpRequest = TeleportRequest.getTeleportRequest(uuid);

		if(tpRequest != null) {
			player.sendMessage(youAlreadySentATeleportText(otherPlayerTpr.getName()));
			return;
		}


		// Send Other Player a teleport request.
		otherPlayerTpr.sendMessage(playerSentTeleportRequest(player.getName()));
		otherPlayerTpr.sendMessage(teleportRequestDecision(player.getName()));

		new TeleportRequest(player.getUniqueId(), otherPlayerTpr.getUniqueId());

		// Let requester know it was sent.
		player.sendMessage(teleportRequestSent(otherPlayerTpr.getName()));
	}

	private Component teleportRequestDecision(String requester) {
		TextComponent tprMessageAccept = Component.text().append(TextHandler.setText("[Accept", "#03AC13", "BOLD")).clickEvent(ClickEvent.runCommand("/tpraccept " + requester)).build();
		TextComponent tprMessageDecline = Component.text().append(TextHandler.setText("[Decline]", "#B90E0A", "BOLD")).clickEvent(ClickEvent.runCommand("/tprdeny")).build();
        return Component.text().append(tprMessageAccept).append(Component.text().content(ChatColor.GRAY + " : ")).append(tprMessageDecline).build();
	}
}