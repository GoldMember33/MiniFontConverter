package me.goldmember33.converter.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.goldmember33.converter.MiniFontConverterPlugin;
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

            //TextComponent messageConvertedTextComponent = (TextComponent) MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.CONVERTED_MESSAGE_OUTPUT);
            //convertedToMiniFontComponent = messageConvertedTextComponent.append(convertedToMiniFontComponent);

            player.sendMessage(convertedToMiniFontComponent);

            if (plugin.getConfig().getBoolean("settings.set-copy-converted-message-contents-option", true)) {
                player.sendMessage(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.CONVERTED_MESSAGE_COPIED));
            }
        }
    }

    public static Component buildConvertedMessageComponent(Plugin plugin, String message) {
        Component baseComponent = MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.CONVERTED_MESSAGE_OUTPUT);

        Component convertedText = Component.text(message);

        Component suggestComponent = MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.SUGGEST_TEXT)
                .hoverEvent(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.CLICK_TO_SUGGEST))
                .clickEvent(ClickEvent.suggestCommand(message));

        baseComponent = baseComponent
                .append(convertedText)
                .append(suggestComponent);

        if (plugin.getConfig().getBoolean("settings.set-copy-converted-message-contents-option", true)) {
            Component copyComponent = MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.COPY_TEXT)
                    .hoverEvent(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.CLICK_TO_COPY_CLIPBOARD))
                    .clickEvent(ClickEvent.copyToClipboard(message));

            baseComponent = baseComponent.append(copyComponent);
        }

        return baseComponent;
    }
}