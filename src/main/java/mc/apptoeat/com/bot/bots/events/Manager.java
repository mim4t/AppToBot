package mc.apptoeat.com.bot.bots.events;

import lombok.Getter;
import mc.apptoeat.com.bot.botevents.BotEvent;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.objects.PlayerRunnable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.List;

@Getter
public class Manager {

    public SettingManager settingManager;

    public final Bot bot;

    public Manager(Bot bot) {
        this.bot = bot;
        settingManager = bot.getSettingManager();
    }

    public void globalAction(PlayerRunnable runnable) {
        getBot().getViewsManager().getPlayers().forEach(player -> {
            runnable.run(player, 0);
        });
    }

    public void debug(String string) {
        getBot().getViewsManager().getPlayers().forEach(player -> player.sendMessage(Color.code("&3Debug&7: " + string)));
    }

    public void addPlayerToList(Player player) {}

    public void callEventAsync(Event event) {
        for (BotEvent events : main.getInstance().getEventManager().getEvents()) {
            events.call(event);
        }
    }

    public void callEvent(Event event) {
        for (BotEvent events : main.getInstance().getEventManager().getEvents()) {
            events.call(event);
        }
    }


    public void queueUpdate() {}
    public void tickPre() {}
    public void tickPost() {}
    public void tickMid() {}

    public double getDistance() {
        if (!bot.getMovements().getTargetLocate().getTargetEntity().getLocation().getWorld().equals(bot.getMovements().getLocation().getWorld())) return -1;
        return bot.getMovements().getTargetLocate().getTargetEntity().getLocation().distance(bot.getMovements().getLocation());
    }
}
