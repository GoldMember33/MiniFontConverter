package me.goldmember33.converter.manager;

import me.goldmember33.converter.MiniFontConverterPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class PlayerDataManager {

    private final File playerDataFolder;

    public PlayerDataManager() {
        this.playerDataFolder = new File(MiniFontConverterPlugin.getInstance().getDataFolder()
        + File.separator + "data");

        if (!this.playerDataFolder.exists() && !this.playerDataFolder.mkdirs()) {
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            this.createPlayerDataFile(p);
        }
    }

    public void createPlayerDataFile(@NotNull Player player) {
        UUID playerUUID = player.getUniqueId();

        File playerDataFile = new File(playerDataFolder, playerUUID + ".yml");

        if (!playerDataFile.exists()) {
            try {
                if (playerDataFile.createNewFile()) {
                    MiniFontConverterPlugin.getInstance().getLogger().info(MiniFontConverterPlugin.PLAYER_DATA_FILE_CREATED
                            .replace("{player}", player.getName()));
                } else {
                    MiniFontConverterPlugin.getInstance().getLogger().warning(MiniFontConverterPlugin.PLAYER_DATA_FILE_CREATION_FAILED
                            .replace("{player}", player.getName()));
                }
            } catch (Exception e) {
                MiniFontConverterPlugin.getInstance().getLogger().severe(MiniFontConverterPlugin.PLAYER_DATA_FILE_CREATION_ERROR
                        .replace("{player}", player.getName())
                        .replace("{error}", e.getMessage()));
            }
        }
    }

    public File getPlayerDataFile(@NotNull Player player) {
        return new File(playerDataFolder, player.getUniqueId() + ".yml");
    }

    public YamlConfiguration getPlayerDataConfig(@NotNull Player player) {
        return YamlConfiguration.loadConfiguration(this.getPlayerDataFile(player));
    }

    public void setDataToPlayerConfig(Player player, boolean flagValue) {
        YamlConfiguration playerDataConfig = getPlayerDataConfig(player);
        if (playerDataConfig.getConfigurationSection("data") == null) {
            playerDataConfig.createSection("data");
            try {
                playerDataConfig.save(this.getPlayerDataFile(player));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        playerDataConfig.set("data." + player.getUniqueId() + ".ign", player.getName());
        playerDataConfig.set("data." + player.getUniqueId() + ".chat-feature-enabled", flagValue);

        try {
            playerDataConfig.save(this.getPlayerDataFile(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadData() {
        for (File file : Objects.requireNonNull(playerDataFolder.listFiles())) {
            YamlConfiguration.loadConfiguration(file);
        }
    }
}