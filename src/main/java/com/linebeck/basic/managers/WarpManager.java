package com.linebeck.basic.managers;

import com.linebeck.basic.data.WarpData;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

public class WarpManager {

    private final TextComponent separator = Component.text("------------------")
            .color(TextColor.fromCSSHexString(Main.getInstance().getMainHexColor()))
            .decoration(TextDecoration.ITALIC, false);

    private final WarpData warpData = new WarpData();

    public WarpData getWarpData() {
        return warpData;
    }

    public void showList(Player player) {
        var warps = getWarpData().getWarps();

        if(warps.isEmpty()) {
            player.sendMessage(TextHandler.setText("There are no warps!", Main.getInstance().getMainHexColor()));
            return;
        }

        TextComponent warpText = Component.empty();

        for(int i=0; i < warps.size(); i++) {
            var warp = warps.get(i);

            warpText = warpText.append(TextHandler.setText(TextHandler.captilize(warp.getName()), Main.getInstance().getSubHexColor())
                    .clickEvent(ClickEvent.runCommand("/warp " + warp.getName())));

            if(i != warps.size() - 1) {
                warpText = warpText.append(TextHandler.setText(", ", Main.getInstance().getMainHexColor()));
            }
        }

        player.sendMessage(separator);
        player.sendMessage(warpText);
        player.sendMessage(separator);
    }
}