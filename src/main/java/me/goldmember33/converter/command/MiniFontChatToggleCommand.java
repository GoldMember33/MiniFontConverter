package me.goldmember33.converter.command;

import me.goldmember33.converter.MiniFontConverterPlugin;
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
            sender.sendMessage(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.NO_PERMISSION));
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.COMMAND_ONLY_PLAYER));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(MiniFontConverterPlugin
                    .deserialize(MiniFontConverterPlugin.TOGGLE_CHAT_FEATURE_COMMAND_CORRECT_USAGE));
            return false;
        }

        if (args.length == 1) {
            String flagArg = args[0].toLowerCase();
            if (!flagArg.equalsIgnoreCase("toggle") && !MiniFontConverterPlugin.isBoolean(flagArg)) {
                player.sendMessage(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.REQUIRES_VALID_FLAG_FOR_CHAT_TOGGLE_FEATURE_USAGE));
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
            player.sendMessage(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.TOGGLE_CHAT_FEATURE_STATUS
                    .replace("{status}",
                            String.valueOf(flag
                                    ? MiniFontConverterPlugin.STATUS_ENABLED
                                    : MiniFontConverterPlugin.STATUS_DISABLED))));

            return true;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
                                                @NotNull String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("true", "false", "toggle"), new ArrayList<>());
        }

        return List.of();
    }
}
