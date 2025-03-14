package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

// Repair item in hand or entire inventory.
public class RepairCommand extends BaseCommand {

	private final Component cantRepairEmptyText = TextHandler.setText(
			"You cannot repair air?!?!",
			Main.getInstance().getMainHexColor()
	);

	private final Component notRepairableText = TextHandler.setText(
			"This is not a repairable item!",
			Main.getInstance().getMainHexColor()
	);

	private final Component successRepairText = TextHandler.setText(
			"You repaired your item!",
			Main.getInstance().getMainHexColor()
	);

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /repair <Optional:[all]>",
			Main.getInstance().getMainHexColor()
	);

	private final Component allRepairText = TextHandler.setText(
			"All items have been repaired!",
			Main.getInstance().getMainHexColor()
	);

	public RepairCommand() {
		super(
				"repair",
				"basic.repair",
				false,
				new ArrayList<>(
						Arrays.asList(
								"all"
						)
				)
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length < 1) {
			ItemStack itemToRepair = player.getInventory().getItemInMainHand();

			if(itemToRepair == null || itemToRepair.getType() == Material.AIR) {
				player.sendMessage(cantRepairEmptyText);
				return;
			}

			ItemMeta itemToRepairMeta = itemToRepair.getItemMeta();

			if(!(itemToRepairMeta instanceof Damageable)) {
				player.sendMessage(notRepairableText);
				return;
			}

			((Damageable) itemToRepairMeta).setDamage(0);
			itemToRepair.setItemMeta(itemToRepairMeta);

			player.sendMessage(successRepairText);
			return;
		}

		if(!args[0].equalsIgnoreCase(super.getArguments().getFirst())) {
			player.sendMessage(syntaxText);
			return;
		}

		for(ItemStack itemToRepair : player.getInventory().getContents()) {
			if(itemToRepair == null || itemToRepair.getType() == Material.AIR) { continue; }
			ItemMeta itemToRepairMeta = itemToRepair.getItemMeta();

			if(!(itemToRepairMeta instanceof Damageable)) { continue; }

			((Damageable) itemToRepairMeta).setDamage(0);
			itemToRepair.setItemMeta(itemToRepairMeta);
		}

		player.sendMessage(allRepairText);
	}
}