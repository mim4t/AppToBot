package mc.apptoeat.com.bot.utils;

import org.bukkit.ChatColor;

public class Color {
    public static String code(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
