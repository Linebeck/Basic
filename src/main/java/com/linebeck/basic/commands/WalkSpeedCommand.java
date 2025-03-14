package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.utilities.NumericUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WalkSpeedCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText(
			"Syntax: /walkspeed <number>",
			Main.getInstance().getMainHexColor()
	);

	private Component youSetWalkSpeedText(float value) {
		return TextHandler.setText(
				"You have set your walk speed to ",
				Main.getInstance().getMainHexColor()
				).append(
						TextHandler.setText(
								String.valueOf(value),
								Main.getInstance().getSubHexColor()
						)
				).append(
						TextHandler.setText(
								"!",
								Main.getInstance().getMainHexColor()
						)
		);
	}

	public WalkSpeedCommand() {
		super(
				"walkspeed",
				"basic.walkspeed",
				false
		);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if(args.length < 1) {
			player.sendMessage(syntaxText);
			return;
		}

		if(!NumericUtil.isNumeric(args[0])) {
			player.sendMessage(BasicTexts.notNumericValuesText);
			return;
		}

		float walkSpeed = Float.parseFloat(args[0]);

		if (walkSpeed >= 1.0f) {
			walkSpeed = 1.0f;
		}

		if(walkSpeed <= -1.0f) {
			walkSpeed = -1.0f;
		}

		player.setWalkSpeed(walkSpeed);

		player.sendMessage(youSetWalkSpeedText(walkSpeed));
	}
}