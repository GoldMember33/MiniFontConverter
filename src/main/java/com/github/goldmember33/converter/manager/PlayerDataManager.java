package com.github.goldmember33.converter.manager;

import com.github.goldmember33.converter.MiniFontConverterPlugin;
import com.github.goldmember33.converter.configuration.MessageValues;
import com.github.goldmember33.converter.utilities.LoggingUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
                    MiniFontConverterPlugin.getInstance().getLogger().info(MessageValues.PLAYER_DATA_FILE_CREATED
                            .replace("{player}", player.getName()));
                } else {
                    MiniFontConverterPlugin.getInstance().getLogger().warning(MessageValues.PLAYER_DATA_FILE_CREATION_FAILED
                            .replace("{player}", player.getName()));
                }
            } catch (Exception e) {
                MiniFontConverterPlugin.getInstance().getLogger().severe(MessageValues.PLAYER_DATA_FILE_CREATION_ERROR
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

            } catch (IOException ioException) {
                LoggingUtils.logError("Erro ao salvar o arquivo de dados do jogador com nome: " +
                        player.getName(), ioException);
            } catch (Exception exception) {
                LoggingUtils.logError("Erro desconhecido ao salvar o arquivo de dados do jogador com nome: " +
                        player.getName(), exception);
            }
        }

        playerDataConfig.set("data." + player.getUniqueId() + ".ign", player.getName());
        playerDataConfig.set("data." + player.getUniqueId() + ".chat-feature-enabled", flagValue);

        try {
            playerDataConfig.save(this.getPlayerDataFile(player));

        } catch (IOException ioException) {
            LoggingUtils.logError("Erro ao salvar o arquivo de dados do jogador com nome: " +
                    player.getName(), ioException);
        } catch (Exception exception) {
            LoggingUtils.logError("Erro desconhecido ao salvar o arquivo de dados do jogador com nome: " +
                    player.getName(), exception);
        }
    }

    public void reloadData() {
        for (File file : Objects.requireNonNull(playerDataFolder.listFiles())) {
            YamlConfiguration.loadConfiguration(file);
        }
    }

    public HashMap<UUID, Boolean> loadChatFeatureEnabledMap() {
        HashMap<UUID, Boolean> map = new HashMap<>();
        for (File file : Objects.requireNonNull(playerDataFolder.listFiles())) {
            YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(file);
            UUID playerUUID = UUID.fromString(file.getName().replace(".yml", ""));
            boolean chatFeatureEnabled = playerDataConfig.getBoolean("data." + playerUUID + ".chat-feature-enabled", false);
            map.put(playerUUID, chatFeatureEnabled);
        }

        return map;
    }
}