package com.github.goldmember33.converter.command;

import com.github.goldmember33.converter.MiniFontConverterPlugin;
import com.github.goldmember33.converter.configuration.MessageValues;
import com.github.goldmember33.converter.listener.ChatListener;
import com.github.goldmember33.converter.utilities.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MiniFontCommand implements CommandExecutor, TabCompleter {

    private MiniFontConverterPlugin plugin;

    public MiniFontCommand(MiniFontConverterPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("minifontconverter.convert.use")) {
            sender.sendMessage(ColorUtils.deserialize(MessageValues.NO_PERMISSION));
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(ColorUtils.deserialize(MessageValues.COMMAND_CORRECT_USAGE));
            return false;
        }

        String message = String.join(" ", args);
        String convertedMessage = MiniFontConverterPlugin.convertToMiniFont(
                PlainTextComponentSerializer.plainText().serialize(Component.text(message)));

        Component convertedToMiniFontComponent = ChatListener.buildConvertedMessageComponent(plugin, convertedMessage);

        if (sender instanceof ConsoleCommandSender cs) {
            cs.sendMessage(convertedToMiniFontComponent);
        }

        if (sender instanceof Player player) {
            player.sendMessage(convertedToMiniFontComponent);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
                                                @NotNull String[] args) {

        if (!sender.hasPermission("minifontconverter.convert.use")) {
            return List.of();
        }

        if (args.length == 1) {
            return List.of("<message>", "<mensagem>", "<entrada>", "<input>", "example", "exemplo");
        }

        return List.of();
    }
}
