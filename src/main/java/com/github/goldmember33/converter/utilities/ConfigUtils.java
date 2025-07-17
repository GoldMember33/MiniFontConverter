package com.github.goldmember33.converter.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigUtils {

    /**
     * Reloads a configuration file.
     *
     * @param file: The file to reload.
     */
    public static FileConfiguration reloadConfig(@NotNull File file) {

        FileConfiguration configuration = null;
        try {
            configuration = YamlConfiguration.loadConfiguration(file);
        } catch (Exception exception) {
            LoggingUtils.logError("Erro desconhecido ao carregar o arquivo de configuração: " + file.getName(),
                    exception);
        }

        return configuration;
    }
}
