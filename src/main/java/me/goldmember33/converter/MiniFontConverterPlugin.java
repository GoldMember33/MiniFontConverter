package me.goldmember33.converter;

import me.goldmember33.converter.command.MiniFontChatToggleCommand;
import me.goldmember33.converter.command.MiniFontCommand;
import me.goldmember33.converter.command.MiniFontReloadCommand;
import me.goldmember33.converter.data.PlayerData;
import me.goldmember33.converter.listener.ChatListener;
import me.goldmember33.converter.listener.PlayerJoinListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class MiniFontConverterPlugin extends JavaPlugin {

    private static MiniFontConverterPlugin instance;

    private PlayerData playerData;

    // Data
    private HashMap<UUID, Boolean> chatFeatureEnabledMap;

    // Messages
    public static String COMMAND_CORRECT_USAGE;
    public static String TOGGLE_CHAT_FEATURE_COMMAND_CORRECT_USAGE;
    public static String STATUS_ENABLED;
    public static String STATUS_DISABLED;
    public static String TOGGLE_CHAT_FEATURE_STATUS;
    public static String CLICK_TO_COPY_CLIPBOARD;
    public static String CONVERTED_MESSAGE_OUTPUT;
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

        this.getConfig().options().copyDefaults(true);
        this.getConfig().options().parseComments(true);
        this.saveDefaultConfig();

        this.chatFeatureEnabledMap = new HashMap<>();
        this.playerData = new PlayerData();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

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
                "<blue>Clique para copiar a entrada</blue>");
        CONVERTED_MESSAGE_OUTPUT = this.getConfig().getString("messages.converted-message-output",
        "<green>Mensagem convertida para MiniFont: </green> ");
        NO_PERMISSION = this.getConfig().getString("messages.no-permission",
                "<red>Você não tem permissão para executar este comando!</red>");
        COMMAND_ONLY_PLAYER = this.getConfig().getString("messages.command-only-player",
                "<red>Este comando só pode ser usado por jogadores!</red>");
        REQUIRES_VALID_FLAG_FOR_CHAT_TOGGLE_FEATURE_USAGE = this.getConfig().getString("messages.requires-valid-flag-for-chat-toggle-feature-usage",
                "<red>Uso correto: /minifontchat <toggle/true/false></red>");
        PLAYER_DATA_FILE_CREATED = this.getConfig().getString("messages.player-data-file-created",
        "<blue>Player data file created for:</blue> <white>{player}</white>");
        PLAYER_DATA_FILE_CREATION_FAILED = this.getConfig().getString("messages.player-data-file-creation-failed",
                "<red>Failed to create player data file for:</red> <white>{player}</white>");
        PLAYER_DATA_FILE_CREATION_ERROR = this.getConfig().getString("messages.player-data-file-creation-error",
                "<red>Error creating player data file for:</red> <white>{player}</white>: <dark_red>{error}</dark_red>");
        RELOADED_CONFIGURATIONS = this.getConfig().getString("messages.reloaded-configurations",
                "<green>All configurations reloaded!</green>");

        this.getCommand("minifontconverter").setExecutor(new MiniFontCommand(this));
        this.getCommand("minifontconverter").setTabCompleter(new MiniFontCommand(this));

        this.getCommand("minifontchatoggle").setExecutor(new MiniFontChatToggleCommand(this));
        this.getCommand("minifontchatoggle").setTabCompleter(new MiniFontChatToggleCommand(this));

        this.getCommand("minifontreload").setExecutor(new MiniFontReloadCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
        this.chatFeatureEnabledMap.clear();
    }

    public void reloadAllConfigurations() {
        this.reloadConfig();
        this.playerData.reloadData();
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

    public PlayerData getPlayerData() {
        return playerData;
    }

    public HashMap<UUID, Boolean> getChatFeatureEnabledMap() {
        return chatFeatureEnabledMap;
    }
}
