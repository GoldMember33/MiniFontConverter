package com.github.goldmember33.converter.listener;

import com.github.goldmember33.converter.MiniFontConverterPlugin;
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
            MiniFontConverterPlugin.getInstance().getPlayerDataManager().createPlayerDataFile(player);
            MiniFontConverterPlugin.getInstance().getChatFeatureEnabledMap().put(player.getUniqueId(), false);
        } else {
            YamlConfiguration playerConfig = MiniFontConverterPlugin.getInstance().getPlayerDataManager().getPlayerDataConfig(player);
            boolean value = playerConfig.getBoolean("data." + player.getUniqueId() + ".chat-feature-enabled", false);
            MiniFontConverterPlugin.getInstance().getChatFeatureEnabledMap().putIfAbsent(player.getUniqueId(), value);
            MiniFontConverterPlugin.getInstance().getPlayerDataManager().setDataToPlayerConfig(player, value);
        }
    }
}
