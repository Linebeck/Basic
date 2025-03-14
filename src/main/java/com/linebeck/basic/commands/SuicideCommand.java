package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Kill yourself by command.
public class SuicideCommand extends BaseCommand {

	private final Component suicideText = TextHandler.setText(
			"You have commited suicide!",
			Main.getInstance().getMainHexColor()
	);

	public SuicideCommand()	{
		super(
				"suicide",
				"basic.suicide",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		player.setHealth(0);
		player.sendMessage(suicideText);
	}
}