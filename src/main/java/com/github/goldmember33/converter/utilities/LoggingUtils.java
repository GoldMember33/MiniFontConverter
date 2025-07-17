package com.github.goldmember33.converter.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggingUtils {

    private static final ComponentLogger componentLogger = ComponentLogger.logger();

    /**
     * Logs a debug message to the console.
     *
     * @param message: The message to log.
     */
    public static void logDebug(@NotNull String message) {
        logDebug(message, null);
    }

    /**
     * Logs a debug message to the console.
     *
     * @param component: The component with the message to log.
     */
    public static void logDebug(@NotNull Component component) {
        logDebug(component, null);
    }

    /**
     * Logs a debug message to the console.
     *
     * @param component: The component with the message to log.
     * @param throwable: The throwable to log.
     */
    public static void logDebug(@NotNull Component component, Throwable throwable) {
        if (throwable != null) {
            componentLogger.debug(component, throwable);
        } else {
            componentLogger.debug(component);
        }
    }

    /**
     * Logs a debug message to the console.
     *
     * @param message: The message to log.
     * @param throwable: The throwable to log.
     */
    public static void logDebug(@NotNull String message, Throwable throwable) {
        logDebug(ColorUtils.deserialize(message), throwable);
    }

    /**
     * Logs an info message to the console.
     *
     * @param message: The message to log.
     */
    public static void logInfo(@NotNull String message) {
        componentLogger.info(message);
    }

    /**
     * Logs an info message to the console.
     *
     * @param component: The component with the message to log.
     */
    public static void logInfo(@NotNull Component component) {
        componentLogger.info(component);
    }

    /**
     * Logs a warning message to the console.
     *
     * @param message: The message to log.
     */
    public static void logWarning(@NotNull String message) {
        logWarning(ColorUtils.deserialize(message), null);
    }

    /**
     * Logs a warning message to the console.
     *
     * @param component: The component with the message to log.
     */
    public static void logWarning(@NotNull Component component) {
        logWarning(component, null);
    }

    /**
     * Logs a warning message to the console.
     *
     * @param component: The component with the message to log.
     * @param throwable: The throwable to log.
     */
    public static void logWarning(@NotNull Component component, Throwable throwable) {
        if (throwable != null) {
            componentLogger.warn(component, throwable);
        } else {
            componentLogger.warn(component);
        }
    }

    /**
     * Logs a warning message to the console.
     *
     * @param message: The message to log.
     * @param throwable: The throwable to log.
     */
    public static void logWarning(@NotNull String message, Throwable throwable) {
        logWarning(ColorUtils.deserialize(message), throwable);
    }

    /**
     * Logs an error message to the console.
     *
     * @param message: The message to log.
     */
    public static void logError(@NotNull String message) {
        logError(message, null);
    }

    /**
     * Logs an error message to the console.
     *
     * @param component: The component with the message to log.
     */
    public static void logError(@NotNull Component component) {
        logError(component, null);
    }

    /**
     * Logs an error message to the console.
     *
     * @param component: The component with the message to log.
     * @param throwable: The throwable to log.
     */
    public static void logError(@NotNull Component component, Throwable throwable) {
        if (throwable != null) {
            componentLogger.error(component, throwable);
        } else {
            componentLogger.error(component);
        }
    }

    /**
     * Logs an error message to the console.
     *
     * @param message: The message to log.
     * @param throwable: The throwable to log.
     */
    public static void logError(@NotNull String message, Throwable throwable) {
        logError(ColorUtils.deserialize(message), throwable);
    }
}