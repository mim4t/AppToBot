package mc.apptoeat.com.bot.utils.objects;

import mc.apptoeat.com.bot.bots.setting.SettingObject;
import org.bukkit.entity.Player;

import java.io.IOException;

public interface SettingObjectRunnable {
    public abstract void run(SettingObject settingObject);
}
