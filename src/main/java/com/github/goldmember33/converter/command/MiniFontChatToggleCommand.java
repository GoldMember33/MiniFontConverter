package com.github.goldmember33.converter.command;

import com.github.goldmember33.converter.MiniFontConverterPlugin;
import com.github.goldmember33.converter.configuration.MessageValues;
import com.github.goldmember33.converter.utilities.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiniFontChatToggleCommand implements CommandExecutor, TabCompleter {

    private MiniFontConverterPlugin plugin;

    public MiniFontChatToggleCommand(MiniFontConverterPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("minifontconverter.convert.chat.use")) {
            sender.sendMessage(ColorUtils.deserialize(MessageValues.NO_PERMISSION));
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtils.deserialize(MessageValues.COMMAND_ONLY_PLAYER));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(ColorUtils
                    .deserialize(MessageValues.TOGGLE_CHAT_FEATURE_COMMAND_CORRECT_USAGE));
            return false;
        }

        if (args.length == 1) {
            String flagArg = args[0].toLowerCase();
            if (!flagArg.equalsIgnoreCase("toggle") && !MiniFontConverterPlugin.isBoolean(flagArg)) {
                player.sendMessage(ColorUtils.deserialize(MessageValues.REQUIRES_VALID_FLAG_FOR_CHAT_TOGGLE_FEATURE_USAGE));
                return false;
            }

            YamlConfiguration playerDataConfig = plugin.getPlayerDataManager().getPlayerDataConfig(player);
            boolean flag = playerDataConfig.getBoolean("data." + player.getUniqueId() + ".chat-feature-enabled");
            if (flagArg.equalsIgnoreCase("toggle")) {
                flag = !flag;
            }

            else if (flagArg.equalsIgnoreCase("true")) {
                flag = true;
            } else if (flagArg.equalsIgnoreCase("false")) {
                flag = false;
            }

            plugin.getChatFeatureEnabledMap().put(player.getUniqueId(), flag);
            plugin.getPlayerDataManager().setDataToPlayerConfig(player, flag);
            player.sendMessage(ColorUtils.deserialize(MessageValues.TOGGLE_CHAT_FEATURE_STATUS
                    .replace("{status}",
                            String.valueOf(flag
                                    ? MessageValues.STATUS_ENABLED
                                    : MessageValues.STATUS_DISABLED))));

            return true;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
                                                @NotNull String[] args) {

        if (!sender.hasPermission("minifontconverter.convert.chat.use")) {
            return List.of();
        }

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("true", "false", "toggle"), new ArrayList<>());
        }

        return List.of();
    }
}
