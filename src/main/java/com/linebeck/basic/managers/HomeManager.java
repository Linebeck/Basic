package com.linebeck.basic.managers;

import com.linebeck.basic.data.HomeData;
import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

public class HomeManager {

    private final TextComponent separator = Component.text("------------------")
            .color(TextColor.fromCSSHexString(Main.getInstance().getMainHexColor()))
            .decoration(TextDecoration.ITALIC, false);

    private final HomeData homeData = new HomeData();

    public HomeData getHomeData() {
        return homeData;
    }

    public void showList(Player player) {
        var homes = getHomeData().getHomes(player.getUniqueId());

        if(homes.isEmpty()) {
            player.sendMessage(
                    TextHandler.setText(
                            "You do not have any homes!",
                            Main.getInstance().getMainHexColor()
                    )
            );
            return;
        }

        TextComponent homesText = Component.empty();

        for(int i=0; i < homes.size(); i++) {
            var home = homes.get(i);

            homesText = homesText.append(TextHandler.setText(TextHandler.captilize(home.getName()), Main.getInstance().getSubHexColor())
                    .clickEvent(ClickEvent.runCommand("/home " + home.getName())));

            if(i != homes.size() - 1) {
                homesText = homesText.append(TextHandler.setText(", ", Main.getInstance().getMainHexColor()));
            }
        }

        player.sendMessage(separator);
        player.sendMessage(homesText);
        player.sendMessage(separator);
    }
}
