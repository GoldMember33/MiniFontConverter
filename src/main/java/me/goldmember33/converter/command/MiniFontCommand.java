package me.goldmember33.converter.command;

import me.goldmember33.converter.MiniFontConverterPlugin;
import me.goldmember33.converter.listener.ChatListener;
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
            sender.sendMessage(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.NO_PERMISSION));
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.COMMAND_CORRECT_USAGE));
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

            if (plugin.getConfig().getBoolean("settings.set-copy-converted-message-contents-option", true)) {
                player.sendMessage(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.CONVERTED_MESSAGE_COPIED));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
                                                @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("<message>", "<mensagem>", "<entrada>", "<input>", "example", "exemplo");
        }

        return List.of();
    }
}
