package com.github.goldmember33.converter.configuration;

import com.github.goldmember33.converter.MiniFontConverterPlugin;
import com.github.goldmember33.converter.utilities.ReplacementUtils;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageValues {

    // Message values
    public static String PREFIX;
    public static String MESSAGE_SEPARATOR;

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
    public static String NO_PERMISSION;
    public static String COMMAND_ONLY_PLAYER;
    public static String REQUIRES_VALID_FLAG_FOR_CHAT_TOGGLE_FEATURE_USAGE;
    public static String PLAYER_DATA_FILE_CREATED;
    public static String PLAYER_DATA_FILE_CREATION_FAILED;
    public static String PLAYER_DATA_FILE_CREATION_ERROR;
    public static String RELOADED_CONFIGURATIONS;

    static  {

        // Load messages from the configuration file
        setupMessages();
    }

    public static void setupMessages() {
        // Load messages from the configuration file

        FileConfiguration messages = getMessages();

        PREFIX = messages.getString("messages.prefix",
                "<color:#58d6b0><b>MiniFontConverter</b></color>");
        MESSAGE_SEPARATOR = messages.getString("messages.message-separator",
                "<color:#de008d><b> ➣ </b></color>");

        COMMAND_CORRECT_USAGE = formatPlaceholders(getMessage("command-correct-usage",
                "{prefix}{separator}<red>Uso correto: /minifont</red> <white><mensagem></white>"));
        TOGGLE_CHAT_FEATURE_COMMAND_CORRECT_USAGE = formatPlaceholders(getMessage("toggle-chat-feature-command-correct-usage",
                "<red>Uso correto: /minifontchat</red> <white><true/false></white>"));
        STATUS_ENABLED = formatPlaceholders(getMessage("status-enabled",
                "<green>Ativado</green>"));
        STATUS_DISABLED = formatPlaceholders(getMessage("status-disabled",
                "<red>Desativado</red>"));
        TOGGLE_CHAT_FEATURE_STATUS = formatPlaceholders(getMessage("toggle-chat-feature-status",
                "{prefix}{separator}<green>O status do recurso de chat foi alterado para:</green> <white>{status}</white>"));
        CLICK_TO_COPY_CLIPBOARD = formatPlaceholders(getMessage("click-to-copy-clipboard",
                "<blue>Clique para copiar a entrada para a área de transferência</blue>"));
        COPY_TEXT = formatPlaceholders(getMessage(".copy-text",
                "<aqua> [Copiar]</aqua>"));
        CLICK_TO_SUGGEST = formatPlaceholders(getMessage("click-to-suggest",
                "<blue>Clique para sugerir o conteúdo de texto no chat</blue>"));
        SUGGEST_TEXT = formatPlaceholders(getMessage("suggest-text",
                "<green> [Sugerir texto]</green>"));
        CONVERTED_MESSAGE_OUTPUT = formatPlaceholders(getMessage("converted-message-output",
                "{prefix}{separator}<green>Mensagem convertida para MiniFont: </green> "));
        NO_PERMISSION = formatPlaceholders(getMessage("no-permission",
                "{prefix}{separator}<red>Você não tem permissão para executar este comando!</red>"));
        COMMAND_ONLY_PLAYER = formatPlaceholders(getMessage("command-only-player",
                "{prefix}{separator}<red>Este comando só pode ser utilizado por jogadores!</red>"));
        REQUIRES_VALID_FLAG_FOR_CHAT_TOGGLE_FEATURE_USAGE = formatPlaceholders(getMessage("requires-valid-flag-for-chat-toggle-feature-usage",
                "{prefix}{separator}<red>Uso correto: /minifontchat <toggle/true/false></red>"));
        PLAYER_DATA_FILE_CREATED = formatPlaceholders(getMessage("player-data-file-created",
                "<blue>Os dados foram criados para o jogador com nome:</blue> <white>{player}</white>"));
        PLAYER_DATA_FILE_CREATION_FAILED = formatPlaceholders(getMessage("player-data-file-creation-failed",
                "<red>Houve um erro ao criar o arquivo de dados do jogador com nome:</red> <white>{player}</white>"));
        PLAYER_DATA_FILE_CREATION_ERROR = formatPlaceholders(getMessage("player-data-file-creation-error",
                "<red>Descrição do erro:</red> <dark_red>{error}</dark_red> <red>para o jogador com nome</red> <white>{player}</white>"));
        RELOADED_CONFIGURATIONS = formatPlaceholders(getMessage("reloaded-configurations",
                "{prefix}{separator}<green>Todas os arquivos de configurações foram recarregados!</green>"));
    }

    private static FileConfiguration getMessages() {
        return MiniFontConverterPlugin.getInstance().getMessagesConfig();
    }

    /**
     * Retrieves a message from the message configuration file.
     * If the message is not found, it returns a default message or an error message.
     *
     * @param path:          The path to the message in the configuration file.
     * @param defaultMessage The default message to return if the specified message is not found.
     *                       If null, the method will return the message from the configuration file directly.
     * @return: The message from the configuration file or the default message if not found.
     */
    public static String getMessage(String path, String defaultMessage) {
        FileConfiguration messages = getMessages();

        if (messages.contains("messages." + path)) {
            if (defaultMessage == null) {
                return messages.getString("messages." + path);
            } else {
                return messages.getString("messages." + path, defaultMessage);
            }

        } else {
            return "Message not found for path: messages." + path;
        }
    }

    /**
     * Formats the placeholders in a message using the configured placeholders.
     *
     * @param message: The message to format with placeholders.
     *
     * @return: The formatted message with placeholders replaced.
     */
    public static String formatPlaceholders(String message) {
        return ReplacementUtils.replace(
                message,
                "{prefix}", PREFIX,
                "{separator}", MESSAGE_SEPARATOR
        );
    }
}
