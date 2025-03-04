package mc.apptoeat.com.bot.bots.events;

import mc.apptoeat.com.bot.botevents.BotEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.main;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MEventManager {

    private final Bot bot;
    private final ArrayList<Manager> managers = new ArrayList<>();

    public MEventManager(Bot bot) {
        this.bot = bot;
    }

    public void addEvent(Manager manager) {
        managers.add(manager);
    }

    public void queueUpdate() {
        try {
            managers.forEach(Manager::queueUpdate);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void tickPre() {
        try {
            managers.forEach(Manager::tickPre);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void tickPost() {
        try {
            managers.forEach(Manager::tickPost);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void tickMid() {
        try {
            managers.forEach(Manager::tickMid);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public void callEvents() {
        // Run async
        new BukkitRunnable() {
            @Override
            public void run() {
                List<BotEvent> botEvents = main.getInstance().getEventManager().getEvents();
                for (int i = 0;i < botEvents.size();i++) {
                    BotEvent event = botEvents.get(i);
                    event.callTick(bot);
                }
            }
        }.runTaskAsynchronously(main.getInstance());
    }
}
