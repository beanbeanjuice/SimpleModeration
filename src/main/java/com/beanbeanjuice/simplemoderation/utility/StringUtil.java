package com.beanbeanjuice.simplemoderation.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String translateLegacyCodes(String string) {
        string = replaceEssentialsColorCodes(string);
        return string
                .replace('§', '&')
                .replace("&0", convertToTag(NamedTextColor.BLACK.asHexString()))
                .replace("&1", convertToTag(NamedTextColor.DARK_BLUE.asHexString()))
                .replace("&2", convertToTag(NamedTextColor.DARK_GREEN.asHexString()))
                .replace("&3", convertToTag(NamedTextColor.DARK_AQUA.asHexString()))
                .replace("&4", convertToTag(NamedTextColor.DARK_RED.asHexString()))
                .replace("&5", convertToTag(NamedTextColor.DARK_PURPLE.asHexString()))
                .replace("&6", convertToTag(NamedTextColor.GOLD.asHexString()))
                .replace("&7", convertToTag(NamedTextColor.GRAY.asHexString()))
                .replace("&8", convertToTag(NamedTextColor.DARK_GRAY.asHexString()))
                .replace("&9", convertToTag(NamedTextColor.BLUE.asHexString()))
                .replace("&a", convertToTag(NamedTextColor.GREEN.asHexString()))
                .replace("&b", convertToTag(NamedTextColor.AQUA.asHexString()))
                .replace("&c", convertToTag(NamedTextColor.RED.asHexString()))
                .replace("&d", convertToTag(NamedTextColor.LIGHT_PURPLE.asHexString()))
                .replace("&e", convertToTag(NamedTextColor.YELLOW.asHexString()))
                .replace("&f", convertToTag(NamedTextColor.WHITE.asHexString()))
                .replace("&k", convertToTag("obfuscated"))
                .replace("&l", convertToTag("bold"))
                .replace("&m", convertToTag("strikethrough"))
                .replace("&n", convertToTag("underlined"))
                .replace("&o", convertToTag("italic"))
                .replace("&r", convertToTag("reset"))
                .replace("\\n", convertToTag("newline"))

                .replaceAll("&#([A-Fa-f0-9]{6})", "<#$1>");  // "&#FFC0CBHello! -> <#FFC0CB>Hello!
    }

    public static String replaceEssentialsColorCodes(final String string) {
        Pattern pattern = Pattern.compile("§x(§[0-9a-fA-F]){6}");  // "§x§f§b§6§3§f§5Hello!" -> "&#fb63f5Hello!"
        Matcher matcher = pattern.matcher(string);

        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String hexColor = matcher.group(0)
                    .replace("§x", "")
                    .replace("§", "");
            matcher.appendReplacement(result, "&#" + hexColor);
        }

        matcher.appendTail(result);

        return result.toString();
    }

    private static String convertToTag(String string) {
        return "<" + string + ">";
    }

    public static Component stringToComponent(String string) {
        string = translateLegacyCodes(string);

        return MiniMessage.miniMessage().deserialize(string);
    }

}
