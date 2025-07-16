package me.goldmember33.converter;

import me.goldmember33.converter.command.MiniFontChatToggleCommand;
import me.goldmember33.converter.command.MiniFontCommand;
import me.goldmember33.converter.command.MiniFontReloadCommand;
import me.goldmember33.converter.manager.PlayerDataManager;
import me.goldmember33.converter.listener.ChatListener;
import me.goldmember33.converter.listener.PlayerJoinListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class MiniFontConverterPlugin extends JavaPlugin {

    private static MiniFontConverterPlugin instance;

    private PlayerDataManager playerDataManager;

    // Data
    private HashMap<UUID, Boolean> chatFeatureEnabledMap;

    // Messages
    public static String COMMAND_CORRECT_USAGE;
    public static String TOGGLE_CHAT_FEATURE_COMMAND_CORRECT_USAGE;
    public static String STATUS_ENABLED;
    public static String STATUS_DISABLED;
    public static String TOGGLE_CHAT_FEATURE_STATUS;
    public static String CLICK_TO_COPY_CLIPBOARD;
    public static String COPY_TEXT;
    public static String CLICK_TO_SUGGEST;
    public static String SUGGEST_TEXT;
    public static String CONVERTED_MESSAGE_OUTPUT;
    public static String CONVERTED_MESSAGE_COPIED;
    public static String NO_PERMISSION;
    public static String COMMAND_ONLY_PLAYER;
    public static String REQUIRES_VALID_FLAG_FOR_CHAT_TOGGLE_FEATURE_USAGE;
    public static String PLAYER_DATA_FILE_CREATED;
    public static String PLAYER_DATA_FILE_CREATION_FAILED;
    public static String PLAYER_DATA_FILE_CREATION_ERROR;
    public static String RELOADED_CONFIGURATIONS;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        getLogger().info("Loading MiniFontConverter configurations and messages...");

        this.getConfig().options().copyDefaults(true);
        this.getConfig().options().parseComments(true);
        this.saveDefaultConfig();

        File extraFilesFolder = new File(getDataFolder(), "ExtraFiles");
        if (!extraFilesFolder.exists() && extraFilesFolder.mkdirs()) {

            saveResource("ExtraFiles/readme.txt", false);

            saveResource("ExtraFiles/config_en.yml", false);
            saveResource("ExtraFiles/config_pt.yml", false);
            saveResource("ExtraFiles/config_en_small_caps_font.yml", false);
            saveResource("ExtraFiles/config_pt_small_caps_font.yml", false);

            getLogger().info("All files in ExtraFiles folder eas been created successfully.");

            getLogger().info("ExtraFiles folder created successfully.");
        } else {
            getLogger().warning("Failed to create ExtraFiles folder. It may already exist.");
        }

        this.chatFeatureEnabledMap = new HashMap<>();
        this.playerDataManager = new PlayerDataManager();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);

        COMMAND_CORRECT_USAGE = this.getConfig().getString("messages.command-correct-usage",
                "<red>Uso correto: /minifont</red> <white><mensagem></white>");
        TOGGLE_CHAT_FEATURE_COMMAND_CORRECT_USAGE = this.getConfig().getString("messages.toggle-chat-feature-command-correct-usage",
                "<red>Uso correto: /minifontchat</red> <white><true/false></white>");
        STATUS_ENABLED = this.getConfig().getString("messages.status-enabled",
                "<green>Ativado</green>");
        STATUS_DISABLED = this.getConfig().getString("messages.status-disabled",
                "<red>Desativado</red>");
        TOGGLE_CHAT_FEATURE_STATUS = this.getConfig().getString("messages.toggle-chat-feature-status",
                "<green>O status do recurso de chat foi alterado para:</green> <white>{status}</white>");
        CLICK_TO_COPY_CLIPBOARD = this.getConfig().getString("messages.click-to-copy-clipboard",
                "<blue>Clique para copiar a entrada para a área de transferência</blue>");
        COPY_TEXT = this.getConfig().getString("messages.copy-text",
                "<aqua> [Copiar]</aqua>");
        CLICK_TO_SUGGEST = this.getConfig().getString("messages.click-to-suggest",
                "<blue>Clique para sugerir o conteúdo de texto no chat</blue>");
        SUGGEST_TEXT = this.getConfig().getString("messages.suggest-text",
                "<green> [Sugerir texto]</green>");
        CONVERTED_MESSAGE_OUTPUT = this.getConfig().getString("messages.converted-message-output",
        "<green>Mensagem convertida para MiniFont: </green> ");
        CONVERTED_MESSAGE_COPIED = this.getConfig().getString("messages.converted-message-copied",
                "<green>Mensagem convertida copiada com sucesso!</green>");
        NO_PERMISSION = this.getConfig().getString("messages.no-permission",
                "<red>Você não tem permissão para executar este comando!</red>");
        COMMAND_ONLY_PLAYER = this.getConfig().getString("messages.command-only-player",
                "<red>Este comando só pode ser utilizado por jogadores!</red>");
        REQUIRES_VALID_FLAG_FOR_CHAT_TOGGLE_FEATURE_USAGE = this.getConfig().getString("messages.requires-valid-flag-for-chat-toggle-feature-usage",
                "<red>Uso correto: /minifontchat <toggle/true/false></red>");
        PLAYER_DATA_FILE_CREATED = this.getConfig().getString("messages.player-data-file-created",
                "<blue>Os dados foram criados para o jogador com nome:</blue> <white>{player}</white>");
        PLAYER_DATA_FILE_CREATION_FAILED = this.getConfig().getString("messages.player-data-file-creation-failed",
                "<red>Houve um erro ao criar o arquivo de dados do jogador com nome:</red> <white>{player}</white>");
        PLAYER_DATA_FILE_CREATION_ERROR = this.getConfig().getString("messages.player-data-file-creation-error",
                "<red>Descrição do erro:</red> <dark_red>{error}</dark_red> <red>para o jogador com nome</red> <white>{player}</white>");
        RELOADED_CONFIGURATIONS = this.getConfig().getString("messages.reloaded-configurations",
                "<green>Todas os arquivos de configurações foram recarregados!</green>");

        getLogger().info("Loading MiniFontConverter commands and tab completions...");

        this.getCommand("minifontconverter").setExecutor(new MiniFontCommand(this));
        this.getCommand("minifontconverter").setTabCompleter(new MiniFontCommand(this));

        this.getCommand("minifontchatoggle").setExecutor(new MiniFontChatToggleCommand(this));
        this.getCommand("minifontchatoggle").setTabCompleter(new MiniFontChatToggleCommand(this));

        this.getCommand("minifontreload").setExecutor(new MiniFontReloadCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("Disabling all features of MiniFontConverter...");

        instance = null;
        this.chatFeatureEnabledMap.clear();
    }

    public void reloadAllConfigurations() {
        this.reloadConfig();
        this.playerDataManager.reloadData();
    }

    public static Component deserialize(String message) {
        return MiniMessage.miniMessage().deserialize(message);
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

    public static MiniFontConverterPlugin getInstance() {
        return instance;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public HashMap<UUID, Boolean> getChatFeatureEnabledMap() {
        return chatFeatureEnabledMap;
    }
}
