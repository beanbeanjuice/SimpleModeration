package com.beanbeanjuice.simplemoderation.utility;

import com.beanbeanjuice.simplemoderation.SimpleModeration;
import com.beanbeanjuice.simplemoderation.utility.config.ConfigKey;
import com.beanbeanjuice.simplemoderation.utility.hooks.PlaceholderAPIHook;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class Helper {

    public static void sendMessage(final SimpleModeration plugin, final CommandSender sender, @Nullable final Player hookPlayer, String message) {
        message = plugin.getCustomConfig().get(ConfigKey.PREFIX, String.class) + message;
        if (hookPlayer != null) message = PlaceholderAPIHook.placeholderAPIConversion(hookPlayer, message);
        plugin.getMiniMessage().sender(sender).sendMessage(StringUtil.stringToComponent(message));
    }

    public static void sendTitle(final BukkitAudiences miniMessage, final Player player, String title, String subTitle, Title.Times times) {
        title = PlaceholderAPIHook.placeholderAPIConversion(player, title);
        subTitle = PlaceholderAPIHook.placeholderAPIConversion(player, subTitle);

        miniMessage.sender(player).sendTitlePart(TitlePart.TITLE, StringUtil.stringToComponent(title));
        miniMessage.sender(player).sendTitlePart(TitlePart.SUBTITLE, StringUtil.stringToComponent(subTitle));
        miniMessage.sender(player).sendTitlePart(TitlePart.TIMES, times);
    }

}
