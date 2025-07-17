package com.github.goldmember33.converter.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import com.github.goldmember33.converter.MiniFontConverterPlugin;
import com.github.goldmember33.converter.configuration.MessageValues;
import com.github.goldmember33.converter.utilities.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ChatListener implements Listener {

    private MiniFontConverterPlugin plugin;

    public ChatListener(MiniFontConverterPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player player = e.getPlayer();
        if (MiniFontConverterPlugin.getInstance().getChatFeatureEnabledMap().get(player.getUniqueId())) {
            e.setCancelled(true);

            String convertedMessage = MiniFontConverterPlugin.convertToMiniFont(
                    PlainTextComponentSerializer.plainText().serialize(e.originalMessage()));

            Component convertedToMiniFontComponent = buildConvertedMessageComponent(plugin, convertedMessage);

            player.sendMessage(convertedToMiniFontComponent);
        }
    }

    public static Component buildConvertedMessageComponent(Plugin plugin, String message) {
        Component baseComponent = ColorUtils.deserialize(MessageValues.CONVERTED_MESSAGE_OUTPUT);

        Component convertedText = Component.text(message);

        Component suggestComponent = ColorUtils.deserialize(MessageValues.SUGGEST_TEXT)
                .hoverEvent(ColorUtils.deserialize(MessageValues.CLICK_TO_SUGGEST))
                .clickEvent(ClickEvent.suggestCommand(message));

        baseComponent = baseComponent
                .append(convertedText)
                .append(suggestComponent);

        if (plugin.getConfig().getBoolean("settings.set-copy-converted-message-contents-option", true)) {
            Component copyComponent = ColorUtils.deserialize(MessageValues.COPY_TEXT)
                    .hoverEvent(ColorUtils.deserialize(MessageValues.CLICK_TO_COPY_CLIPBOARD))
                    .clickEvent(ClickEvent.copyToClipboard(message));

            baseComponent = baseComponent.append(copyComponent);
        }

        return baseComponent;
    }
}