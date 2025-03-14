package com.linebeck.basic.interfaces;

import org.bukkit.entity.Player;

public interface PersonalCommand {

    // Only for setting personal arguments for commands. Ex: /home, /delhome
    void setPersonalArguments(Player player);
}