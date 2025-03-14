package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.utilities.NumericUtil;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

// Enchant item.
public class EnchantmentCommand extends BaseCommand {

	private final Component notItemText = TextHandler.setText(
			"No item to enchant!",
			Main.getInstance().getMainHexColor()
	);

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /enchantment [type] [level]",
			Main.getInstance().getMainHexColor()
	);

	private final Component notNumericText = TextHandler.setText(
			"Level must be numeric!",
			Main.getInstance().getMainHexColor()
	);

	private Component allEnchantmentsText(String name) {
		return TextHandler.setText(
				"You have given a ",
				Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						name,
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						" all enchants!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	private final Component invalidEnchantmentText = TextHandler.setText(
			"No such enchantment!",
			Main.getInstance().getMainHexColor()
	);

	private Component successEnchantmentText(String itemName, String enchantmentName, int level) {
		return TextHandler.setText(
				"You have given a ",
						Main.getInstance().getMainHexColor()
		).append(
				TextHandler.setText(
						itemName,
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						" " + enchantmentName + " at level ",
						Main.getInstance().getMainHexColor()
				)
		).append(
				TextHandler.setText(
						String.valueOf(level),
						Main.getInstance().getSubHexColor()
				)
		).append(
				TextHandler.setText(
						"!",
						Main.getInstance().getMainHexColor()
				)
		);
	}

	// All Enchants.
	private static List<String> getEnchants() {
		List<String> enchants = new ArrayList<>();

		var enchantments = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);

		for(Enchantment enchant : enchantments) {
			enchants.add(enchant.getKey().getKey());
		}
		
		enchants.add("all");
		
		return enchants;
	}
	
	public EnchantmentCommand() {
		super(
				"enchantment",
				"basic.enchant",
				false,
				getEnchants()
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length < 1) return;

		ItemStack itemToEnchant = player.getInventory().getItemInMainHand();
		if(itemToEnchant == null || itemToEnchant.getType() == Material.AIR) {
			player.sendMessage(notItemText);
			return;
		}

		String enchantName = args[0];

		if(enchantName.equalsIgnoreCase("all")) {
			applyAllEnchants(itemToEnchant, player, args);
			return;
		}

		var enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantName.toLowerCase()));

		applySingleEnchant(itemToEnchant, enchantment, player, args);

		player.sendMessage(allEnchantmentsText(enchantName));
	}

	// Apply every enchant.
	private void applyAllEnchants(ItemStack item, Player player, String[] args) {
		if(args.length < 2) {
			player.sendMessage(syntaxText);
			return;
		}

		if(!NumericUtil.isNumeric(args[1])) {
			player.sendMessage(notNumericText);
			return;
		}

		int level = Integer.parseInt(args[1]);

		var enchantments = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);

		for(Enchantment enchantment : enchantments) {
			item.addUnsafeEnchantment(enchantment, level);
		}

		var itemName = TextHandler.getPlainText(item.effectiveName());

		player.sendMessage(allEnchantmentsText(itemName));
	}

	// Apply one enchant.
	private void applySingleEnchant(ItemStack item, Enchantment enchantment, Player player, String[] args) {
		if(enchantment == null) {
			player.sendMessage(invalidEnchantmentText);
			return;
		}

		if(args.length < 2) {
			player.sendMessage(syntaxText);
			return;
		}

		if(!NumericUtil.isNumeric(args[1])) {
			player.sendMessage(notNumericText);
			return;
		}

		int level = Integer.parseInt(args[1]);

		item.addUnsafeEnchantment(enchantment, level);

		var itemName = TextHandler.getPlainText(item.getItemMeta().displayName());

		player.sendMessage(
				successEnchantmentText(
						itemName,
						TextHandler.getPlainText(enchantment.displayName(level)),
						level
				)
		);
	}
}
