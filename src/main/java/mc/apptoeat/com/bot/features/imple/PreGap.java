package mc.apptoeat.com.bot.features.imple;

import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.SpawnEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class PreGap extends Feature {

    @Override
    public void listen(Event event) {
        if (event instanceof SpawnEvent) {
            Bot bot = event.bot;
            //Run a tick later so the practice plugin has time to provide you with the items

            new BukkitRunnable() {
                @Override
                public void run() {
                    boolean a = bot.getInventoryManager().hasItem(322).size() > 0;
                    boolean b = bot.getSettingManager().getEatingSettings().getPreGap().isBooleanValue();

                    //Bukkit.broadcastMessage("a " + a + " b " + b);
                    if (a && b) {
                        //Bukkit.broadcastMessage("pre Gap");
                        EatingAndDrinking.eatGap(bot);
                    }
                }
            }.runTaskLaterAsynchronously(main.getInstance(),20 * 6);
        }
    }
}
