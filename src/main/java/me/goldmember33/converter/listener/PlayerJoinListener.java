package me.goldmember33.converter.listener;

import me.goldmember33.converter.MiniFontConverterPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (!player.hasPlayedBefore()) {
            MiniFontConverterPlugin.getInstance().getChatFeatureEnabledMap().put(player.getUniqueId(), false);
        } else {
            YamlConfiguration playerConfig = MiniFontConverterPlugin.getInstance().getPlayerData().getPlayerDataConfig(player);
            boolean value = playerConfig.getBoolean("chat-feature-enabled", false);
            MiniFontConverterPlugin.getInstance().getChatFeatureEnabledMap().putIfAbsent(player.getUniqueId(), value);
            MiniFontConverterPlugin.getInstance().getPlayerData().setDataToPlayerConfig(player, value);
        }
    }
}
