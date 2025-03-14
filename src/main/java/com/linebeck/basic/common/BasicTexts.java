package com.linebeck.basic.common;

import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;

public class BasicTexts {

    // Common Shared Text of a player not being online.
    public static Component playerIsNotOnlineText(String name) {
        return TextHandler.setText(
                name,
                Main.getInstance().getSubHexColor()
        ).append(
                TextHandler.setText(
                        " is not online!",
                        Main.getInstance().getSubHexColor()
                )
        );
    }

    // Common Shared Text of a player not existing.
    public static Component playerDoesNotExist = TextHandler.setText(
            "That player does not exit",
            Main.getInstance().getMainHexColor()
    );

    // Common Shared Text of no numeric values.
    public static Component notNumericValuesText = TextHandler.setText(
            "You must use numeric values!",
            Main.getInstance().getMainHexColor()
    );
}