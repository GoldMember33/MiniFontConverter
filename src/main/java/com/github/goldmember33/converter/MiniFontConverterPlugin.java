package com.github.goldmember33.converter;

import lombok.Getter;
import com.github.goldmember33.converter.command.MiniFontChatToggleCommand;
import com.github.goldmember33.converter.command.MiniFontCommand;
import com.github.goldmember33.converter.command.MiniFontReloadCommand;
import com.github.goldmember33.converter.configuration.MessageValues;
import com.github.goldmember33.converter.manager.PlayerDataManager;
import com.github.goldmember33.converter.listener.ChatListener;
import com.github.goldmember33.converter.listener.PlayerJoinListener;
import com.github.goldmember33.converter.utilities.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

@Getter
public final class MiniFontConverterPlugin extends JavaPlugin {

    @Getter
    private static MiniFontConverterPlugin instance;

    // Files and configurations
    private File messagesFile;

    private FileConfiguration messagesConfig;

    // Managers
    private PlayerDataManager playerDataManager;

    // Data
    private HashMap<UUID, Boolean> chatFeatureEnabledMap;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        this.getLogger().info("Inicializando o MiniFontConverter...");

        this.getLogger().info("Plugin desenvolvido com ❤ por GoldMember33.");

        this.getLogger().info("Carregando os arquivos de configuração e mensagens de MiniFontConverter...");
        this.setupConfigurations();

        // Loading data
        this.chatFeatureEnabledMap = new HashMap<>();
        this.playerDataManager = new PlayerDataManager();

        // Loading listeners
        this.getLogger().info("Carregando os listeners de eventos de MiniFontConverter...");
        this.setupListeners();

        // Loading commands
        this.getLogger().info("Carregando os comandos de MiniFontConverter...");
        this.setupCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        this.getLogger().info("Desativando todos os recursos de MiniFontConverter...");

        instance = null;
        this.chatFeatureEnabledMap.clear();
    }

    private void setupCommands() {
        // Plugin commands setup logic

        this.getCommand("minifontconverter").setExecutor(new MiniFontCommand(this));
        this.getCommand("minifontconverter").setTabCompleter(new MiniFontCommand(this));

        this.getCommand("minifontchatoggle").setExecutor(new MiniFontChatToggleCommand(this));
        this.getCommand("minifontchatoggle").setTabCompleter(new MiniFontChatToggleCommand(this));

        this.getCommand("minifontreload").setExecutor(new MiniFontReloadCommand());
    }

    private void setupConfigurations() {
        // Plugin configurations setup logic

        // Main configuration file
        this.getConfig().options().copyDefaults(true);
        this.getConfig().options().parseComments(true);
        this.saveDefaultConfig();

        // Messages configuration file
        this.messagesFile = new File(this.getDataFolder(), "messages.yml");
        if (!this.messagesFile.exists()) {
            this.saveResource("messages.yml", false);
        }

        this.messagesConfig = YamlConfiguration.loadConfiguration(this.messagesFile);

        this.messagesConfig.options().copyDefaults(true);
        this.messagesConfig.options().parseComments(true);

        // Creating some extra message files
        File extraFilesFolder = new File(getDataFolder(), "ExtraFiles");
        if (!extraFilesFolder.exists() && extraFilesFolder.mkdirs()) {

            this.saveResource("ExtraFiles/readme.txt", false);

            this.saveResource("ExtraFiles/messages_en.yml", false);
            this.saveResource("ExtraFiles/messages_pt.yml", false);
            this.saveResource("ExtraFiles/messages_en_small_caps_font.yml", false);
            this.saveResource("ExtraFiles/messages_pt_small_caps_font.yml", false);

            this.getLogger().info("O diretório ExtraFiles foi criado com sucesso.");

            this.getLogger().info("Todos os arquivos do diretório ExtraFiles foram criados com sucesso.");

        } else {
            this.getLogger().warning("Houve um erro ao criar o diretório ExtraFiles. Pode ser que ele já exista.");
        }
    }

    private void setupListeners() {
        // Plugin listeners setup logic

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
    }

    public void reloadMessages() {
        if (this.messagesFile == null) {
            this.messagesFile = new File(this.getDataFolder(), "messages.yml");
        }

        this.messagesConfig = ConfigUtils.reloadConfig(this.messagesFile);
        MessageValues.setupMessages();
    }

    public void reloadAllConfigurations() {
        // Plugin configurations and data reloading logic

        // Reloading configuration files and values
        this.reloadConfig();
        this.reloadMessages();

        // Reloading data
        this.playerDataManager.reloadData();
    }

    public static boolean isBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    public static String convertToMiniFont(String input) {
        StringBuilder output = new StringBuilder();

        for (char c : input.toCharArray()) {
            switch (c) {
                case 'a', 'A':
                    output.append('ᴀ');
                    break;
                case 'b', 'B':
                    output.append('ʙ');
                    break;
                case 'c', 'C':
                    output.append('ᴄ');
                    break;
                case 'd', 'D':
                    output.append('ᴅ');
                    break;
                case 'e', 'E':
                    output.append('ᴇ');
                    break;
                case 'f', 'F':
                    output.append('ғ');
                    break;
                case 'g', 'G':
                    output.append('ɢ');
                    break;
                case 'h', 'H':
                    output.append('ʜ');
                    break;
                case 'i', 'I':
                    output.append('ɪ');
                    break;
                case 'j', 'J':
                    output.append('ᴊ');
                    break;
                case 'k', 'K':
                    output.append('ᴋ');
                    break;
                case 'l', 'L':
                    output.append('ʟ');
                    break;
                case 'm', 'M':
                    output.append('ᴍ');
                    break;
                case 'n', 'N':
                    output.append('ɴ');
                    break;
                case 'o', 'O':
                    output.append('ᴏ');
                    break;
                case 'p', 'P':
                    output.append('ᴘ');
                    break;
                case 'q', 'Q':
                    output.append('ǫ');
                    break;
                case 'r', 'R':
                    output.append('ʀ');
                    break;
                case 's', 'S':
                    output.append('s');
                    break;
                case 't', 'T':
                    output.append('ᴛ');
                    break;
                case 'u', 'U':
                    output.append('ᴜ');
                    break;
                case 'v', 'V':
                    output.append('ᴠ');
                    break;
                case 'w', 'W':
                    output.append('ᴡ');
                    break;
                case 'x', 'X':
                    output.append('x');
                    break;
                case 'y', 'Y':
                    output.append('ʏ');
                    break;
                case 'z', 'Z':
                    output.append('ᴢ');
                    break;
                default:
                    output.append(c);
                    break;
            }
        }

        return output.toString();
    }

    public FileConfiguration getMessagesConfig() {
        if (this.messagesConfig == null) {
            reloadMessages();
        }

        return this.messagesConfig;
    }
}
