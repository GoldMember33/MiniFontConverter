package com.github.goldmember33.converter.command;

import com.github.goldmember33.converter.MiniFontConverterPlugin;
import com.github.goldmember33.converter.configuration.MessageValues;
import com.github.goldmember33.converter.utilities.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MiniFontReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
                             @NotNull String[] args) {

        if (!sender.hasPermission("minifontconverter.reload")) {
            sender.sendMessage(ColorUtils.deserialize(MessageValues.NO_PERMISSION));
            return false;
        }

        MiniFontConverterPlugin.getInstance().reloadAllConfigurations();
        sender.sendMessage(ColorUtils.deserialize(MessageValues.RELOADED_CONFIGURATIONS));

        return true;
    }
}
