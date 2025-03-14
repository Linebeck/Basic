package com.linebeck.basic.abstracts;

import com.linebeck.basic.handlers.TextHandler;
import com.linebeck.basic.interfaces.PersonalCommand;
import com.linebeck.basic.internal.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCommand implements CommandExecutor, TabCompleter {

    private final String command;
    private final String permission;
    private final boolean canConsoleUse;

    private List<String> arguments;

    // Override for Dynamic Data. Ex: Warps.
    public List<String> getArguments() {
        return arguments;
    }
    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    private final Component noPermission = TextHandler.setText("You do not have permission to use this command!", Main.getInstance().getMainHexColor());

    private final Component canOnlyRunAsPlayer = TextHandler.setText("You may not run this command via console!", Main.getInstance().getMainHexColor());

    private Component getSyntax(String label) {
        return TextHandler.setText("Syntax: ", Main.getInstance().getSubHexColor()).append(TextHandler.setText("/" + label, Main.getInstance().getMainHexColor()));
    }

    public BaseCommand(final String command, final String permission, final boolean canConsoleUse) {
        this.command = command;
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;

        var pluginCommand = Main.getInstance().getCommand(command);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
        }
    }

    public BaseCommand(final String command, final String permission, final boolean canConsoleUse, List<String> arguments) {
        this.command = command;
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;
        this.arguments = arguments;

        var pluginCommand = Main.getInstance().getCommand(command);
        if(pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String @NotNull[] args) {
        if(!command.getLabel().equalsIgnoreCase(this.command)) return true;

        // No Permission.
        if(!sender.hasPermission(permission)) {
            sender.sendMessage(noPermission);
            return true;
        }

        // Console Check.
        if(!canConsoleUse && !(sender instanceof Player)) {
            sender.sendMessage(canOnlyRunAsPlayer);
            return true;
        }

        if(args.length >= 1 && arguments != null) {
            if(!this.arguments.contains(args[0])) {
                sender.sendMessage(getSyntax(command.getLabel()));
                return true;
            }
        }

        execute(sender, args);

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull[] args) {
        // No Permission.
        if(!sender.hasPermission(permission)) return null;

        // Console Check.
        if(!canConsoleUse && !(sender instanceof Player)) return null;

        // Set Personalized Arguments.
        if(this instanceof PersonalCommand personalCommand) {
            personalCommand.setPersonalArguments((Player) sender);
        }

        if(getArguments() == null) return null;
        if(args.length != 1) return null;
        List<String> result = new ArrayList<>();
        for(String argument : getArguments()) {
            if(argument.toLowerCase().startsWith(args[0].toLowerCase())) {
                result.add(argument);
            }
        }
        return result;
    }

    public abstract void execute(CommandSender sender, String[] args);
}