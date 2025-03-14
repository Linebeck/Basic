package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

// More or Stack an Item.
public class MoreCommand extends BaseCommand {

	private final Component nothingInHandText = TextHandler.setText(
			"You do not have anything in your hand!",
			Main.getInstance().getMainHexColor()
	);

	private final Component addedMoreText = TextHandler.setText(
			"You have created more of this item!",
			Main.getInstance().getMainHexColor()
	);

	public MoreCommand() {
		super(
				"more",
				"basic.more",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		ItemStack moreItem = player.getInventory().getItemInMainHand();

		if(moreItem.getType() == Material.AIR) {
			player.sendMessage(nothingInHandText);
			return;
		}

		moreItem.setAmount(64);
		player.sendMessage(addedMoreText);
	}
}