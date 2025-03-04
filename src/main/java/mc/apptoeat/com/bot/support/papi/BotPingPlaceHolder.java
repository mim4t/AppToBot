package mc.apptoeat.com.bot.support.papi;

import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.setting.Setting;
import mc.apptoeat.com.bot.bots.setting.SettingObject;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.MathUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BotPingPlaceHolder extends PlaceholderExpansion {

    @Override
    public @NotNull
    String getIdentifier() {
        return "bot";
    }

    @Override
    public @NotNull String getAuthor() {
        return "crafticat";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable
    String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";

        Bot bot = main.getInstance().getBotManager().getNpcFromTargetView(player);
        if (bot == null) return "";

        for (Setting setting : bot.getSettingManager().getSettings()) {
            for (SettingObject sb : setting.getSettings()) {
                if (params.equals(sb.getName())) return getValue(sb);
            }
        }

        return "invalid type";
    }

    public String getValue(SettingObject sb) {
        if (sb.getObject() instanceof Boolean) {
            return sb.isBooleanValue() + "";
        }
        if (sb.getObject() instanceof Double) {
            return MathUtils.stringOfDouble(sb.getDoubleValue(),3);
        }
        if (sb.getObject() instanceof Integer) {
            return sb.getIntValue() + "";
        }

        return null;
    }
}
