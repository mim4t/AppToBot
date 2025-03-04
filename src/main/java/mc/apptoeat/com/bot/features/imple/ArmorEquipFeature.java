package mc.apptoeat.com.bot.features.imple;

import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotArmorBreakEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ArmorEquipFeature extends Feature {

    @Override
    public void listen(Event event) {
        if (event instanceof BotArmorBreakEvent) {
            BotArmorBreakEvent e = (BotArmorBreakEvent) event;

            int index = e.getArmorSlot();
            Bot bot = e.bot;

            // 1 second delay
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!bot.isAlive()) return;
                    List<ItemStack> stack = bot.getInventoryManager().hasArmor(index);

                    if (stack.isEmpty()) return;
                    bot.getInventoryManager().updateArmorContents(stack.get(0), index);
                    bot.getInventoryManager().removeItem(bot.getInventoryManager().getItemSlot(stack.get(0)));
                }
            }.runTaskLater(main.getInstance(),20);
        }
    }
}
