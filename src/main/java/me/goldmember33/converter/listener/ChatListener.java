package me.goldmember33.converter.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.goldmember33.converter.MiniFontConverterPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player player = e.getPlayer();
        if (MiniFontConverterPlugin.getInstance().getChatFeatureEnabledMap().get(player.getUniqueId())) {
            Component componentMessage = e.originalMessage();
            String convertedMessage = MiniFontConverterPlugin.convertToMiniFont(PlainTextComponentSerializer.plainText().serialize(componentMessage));

            Component convertedToMiniFontComponent = MiniFontConverterPlugin
                    .deserialize(convertedMessage)
                    .hoverEvent(MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.CLICK_TO_COPY_CLIPBOARD))
                    .clickEvent(ClickEvent.copyToClipboard(convertedMessage))
                    .clickEvent(ClickEvent.suggestCommand(convertedMessage));

            TextComponent messageConvertedTextComponent = (TextComponent) MiniFontConverterPlugin.deserialize(MiniFontConverterPlugin.CONVERTED_MESSAGE_OUTPUT);
            convertedToMiniFontComponent = messageConvertedTextComponent.append(convertedToMiniFontComponent);

            player.sendMessage(convertedToMiniFontComponent);
        }
    }
}
