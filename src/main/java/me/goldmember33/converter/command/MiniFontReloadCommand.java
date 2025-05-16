package me.goldmember33.converter.command;

import me.goldmember33.converter.MiniFontConverterPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MiniFontReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
                             @NotNull String[] args) {
        if (!sender.hasPermission("minifontconverter.reload")) {
            sender.sendMessage(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.NO_PERMISSION));
            return false;
        }

        MiniFontConverterPlugin.getInstance().reloadAllConfigurations();
        sender.sendMessage(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.RELOADED_CONFIGURATIONS));

        return true;
    }
}
