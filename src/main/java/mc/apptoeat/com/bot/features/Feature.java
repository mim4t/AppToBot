package mc.apptoeat.com.bot.features;

import mc.apptoeat.com.bot.botevents.BotEvent;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.FinalMoveEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.setting.Setting;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Feature extends BotEvent implements Listener {

    public Feature() {
        main.getInstance().getEventManager().registerEvent(this);
        Bukkit.getServer().getPluginManager().registerEvents(this,main.getInstance());
    }

    public Feature(Plugin plugin) {
        main.getInstance().getEventManager().registerEvent(this);
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }

    /*  Known events */

    public void listen(Event event) {
        if (event instanceof FinalMoveEvent) {
            FinalMoveEvent e = (FinalMoveEvent) event;
            finalMoveEvent(e, event.bot,event.bot.getSettingManager());
        }
    }

    public void finalMoveEvent(FinalMoveEvent event, Bot bot, SettingManager setting) {}
}
