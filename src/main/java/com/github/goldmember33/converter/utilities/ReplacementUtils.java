package com.github.goldmember33.converter.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReplacementUtils {

    /**
     * Replaces placeholders in a message with the provided parameters.
     *
     * @param message: The message String to replace placeholders in.
     * @param params   The parameters to replace in the message. Must be in pairs (key, value).
     * @throws IllegalArgumentException - if the parameters are not in pairs.
     * @return: The message with placeholders replaced.
     */
    public static String replace(String message, String... params) throws IllegalArgumentException {
        if (params.length == 0) {
            return message;
        }

        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Parameters must be in pairs (key, value).");
        }

        String result = message;

        try {
            for (int i = 0; i < params.length; i += 2) {
                String placeholder = params[i];
                String replacement = params[i + 1];
                result = result.replace(placeholder, replacement);
            }

        } catch (Exception exception) {
            LoggingUtils.logError("Erro ao tentar fazer parse de placeholders na mensagem: " + message, exception);
        }

        return result;
    }

    /**
     * Replaces placeholders in a list of messages with the provided parameters.
     *
     * @param messages: The list of messages to replace placeholders in.
     * @param params    The parameters to replace in the messages. Must be in pairs (key, value).
     * @return: The list of messages with placeholders replaced.
     */
    public static List<String> replace(List<String> messages, String... params) {
        messages.replaceAll(message -> replace(message, params));
        return messages;
    }
}