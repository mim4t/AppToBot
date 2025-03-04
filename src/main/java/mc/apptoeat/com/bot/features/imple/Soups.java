package mc.apptoeat.com.bot.features.imple;

import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotDamageEvent;
import mc.apptoeat.com.bot.botevents.events.BotHealthChangeEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.mechanics.InventoryManager;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Soups extends Feature {

    private int soupTime = 5;
    private int soupHealth = 10;

    @Override
    public void listen(Event event) {
        if (event instanceof BotHealthChangeEvent) {
            BotHealthChangeEvent e = (BotHealthChangeEvent) event;
            if (e.getAfter() <= soupHealth) {
                Bot bot = e.bot;
                InventoryManager manager = bot.getInventoryManager();
                List<ItemStack> soups = manager.hasItem(Material.MUSHROOM_SOUP);
                if (soups.size() <= 0) return;

                ItemStack soup = soups.get(0);
                int slot = manager.getItemSlot(soup);
                manager.setMainHand(slot);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!bot.isAlive()) return;
                        manager.setMainHand(1);
                        bot.getHealthManager().addHealth(6);
                        manager.removeItem(slot);
                    }
                }.runTaskLater(main.getInstance(),soupTime);
            }
        }
    }
}
