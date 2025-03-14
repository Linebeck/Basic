package com.linebeck.basic.commands;

import com.linebeck.basic.abstracts.BaseCommand;
import com.linebeck.basic.common.BasicTexts;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import com.linebeck.basic.utilities.NumericUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Flight speed.
public class FlySpeedCommand extends BaseCommand {

	private final Component syntaxText = TextHandler.setText("Syntax: /flyspeed <number>");

	private Component setSpeedText(float value) {
		return TextHandler.setText(
				"You have set your fly speed to ",
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

	public FlySpeedCommand() {
		super(
				"flyspeed",
				"Basic.flyspeed",
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

		float flySpeed = Float.parseFloat(args[0]);

		if(flySpeed >= 1.0f) {
			flySpeed = 1.0f;
		}

		if(flySpeed <= -1.0f) {
			flySpeed = -1.0f;
		}

		player.setFlySpeed(flySpeed);

		player.sendMessage(setSpeedText(flySpeed));
	}
}